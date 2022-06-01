package presenter;

import model.StoredInfoModel.IStoredInfoModel;
import model.searchModel.Search.SearchResult;
import model.listeners.SearchListener;
import model.searchModel.ISearchModel;
import views.IMainView;
import views.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresenter {
    IMainView mainView;
    ISearchView searchView;
    ISearchModel searchModel;
    IStoredInfoModel storedInfoModel;
    List<SearchResult> searchResultsList;
    SearchResult selectedSearchResult;
    Thread thread;

    public SearchPresenter(ISearchModel searchModel, IStoredInfoModel storedInfoModel){
        this.searchModel = searchModel;
        this.storedInfoModel = storedInfoModel;
    }

    @Override
    public void setView(IMainView mainView){
        this.mainView = mainView;
        this.searchView = mainView.getSearchView();
        initListeners();
    }

    private void initListeners(){
        searchModel.addSearchListener(new SearchListener() {
            public void didFindArticleCoincidences() {
                searchResultsList = searchModel.getAllCoincidencesFound();
                if(searchResultsList.isEmpty())
                    notifyInfoToUser("No Coincidences Found");
                else
                    searchView.setSearchResultsList(parseListSearchResult(searchResultsList));
            }
            public void didFindArticleContent() {
                String articleContent = searchModel.getFoundArticleContent();
                searchView.setArticleContent(articleContent);
            }
        });

        searchModel.addErrorListener(errorMessage -> notifyErrorToUser(errorMessage));
    }

    //TODO ver si se puede hacer mejor el siguiente test
    public void setSearchResultsList(List<SearchResult> searchResultsList){
        this.searchResultsList = searchResultsList;
    }

    public void setSelectedSearchResult(SearchResult selectedSearchResult){
        this.selectedSearchResult = selectedSearchResult;
    }

    private List<String> parseListSearchResult(List<SearchResult> searchResultsList){
        List<String> listToReturn = new ArrayList<>();
        String textOfArticle;
        for (SearchResult searchResult: searchResultsList){
            textOfArticle = searchResult.getTitle() + ": " + searchResult.getSnippet();
            textOfArticle = textOfArticle.replace("<span class=\"searchmatch\">", "")
                    .replace("</span>", "");
            listToReturn.add(textOfArticle);
        }
        return listToReturn;
    }

    @Override
    public void onEventSearchArticles() {
        thread = new Thread(() -> {
            //searchView.startWorkingStatus();
            String textToSearch = searchView.getSearchText();
            searchModel.searchAllCoincidencesInWikipedia(textToSearch);
            //searchView.stopWorkingStatus();
        });
        thread.start();
    }

    @Override
    public void onEventSelectArticle() {
        thread = new Thread(() -> {
            //searchView.startWorkingStatus();
            selectedSearchResult = searchResultsList.get(searchView.getSelectedSearchResultIndex());
            if(searchView.fullArticleIsSelected()){
                searchModel.searchFullArticleInWikipedia(selectedSearchResult);
            }else{
                searchModel.searchArticleSummaryInWikipedia(selectedSearchResult);
            }
            //searchView.stopWorkingStatus();
        });
        thread.start();
    }

    @Override
    public void onEventSaveArticle() {
        if(selectedSearchResult != null) {
            String articleTitle = selectedSearchResult.getTitle();
            String articleContent = searchModel.getFoundArticleContent();
            storedInfoModel.saveArticle(articleTitle, articleContent);
        } else
            notifyErrorToUser("Search Result Not Selected");
    }

    private void notifyInfoToUser(String message){
        mainView.notifyMessageToUser(message, "Info");
    }

    private void notifyErrorToUser(String message){
        mainView.notifyMessageToUser(message, "Error");
    }

    public boolean isActivelyWorking(){
        return thread != null && thread.isAlive();
    }
}
