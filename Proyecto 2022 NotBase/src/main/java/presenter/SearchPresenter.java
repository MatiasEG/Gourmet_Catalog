package presenter;

import model.StoredInfoModel.StoredInfoModelInterface;
import model.searchModel.Search.SearchResult;
import model.listeners.SearchListener;
import model.searchModel.SearchModelInterface;
import views.MainView;
import views.MainViewInterface;
import views.SearchView;
import views.SearchViewInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements SearchPresenterInterface {
    MainViewInterface mainView;
    SearchViewInterface searchView;
    SearchModelInterface searchModel;
    StoredInfoModelInterface storedInfoModel;
    List<SearchResult> listOfSearchResults;

    public SearchPresenter(SearchModelInterface searchModel, StoredInfoModelInterface storedInfoModel){
        this.searchModel = searchModel;
        this.storedInfoModel = storedInfoModel;
    }

    @Override
    public void setUp(MainView mainView){
        this.mainView = mainView;
        this.searchView = mainView.getSearchView();
        initListeners();
    }

    private void initListeners(){
        searchModel.addSearchListener(new SearchListener() {
            @Override
            public void didFindArticles() {
                List<SearchResult> articleCoincidences = searchModel.getAllArticleCoincidencesInWikipedia();
                if(articleCoincidences.isEmpty())
                    notifyInfoToUser("No Coincidences Found");
                else {
                    searchView.setSearchResultsList(parseListSearchResult(articleCoincidences));
                    listOfSearchResults = searchModel.getAllArticleCoincidencesInWikipedia();
                }
            }

            @Override
            public void didFindArticleContent() {
                searchView.setArticleContent(searchModel.getSearchedArticleInWikipedia());
            }
        });

        searchModel.addErrorListener(errorMessage -> notifyErrorToUser(errorMessage));
    }

    private List<String> parseListSearchResult(List<SearchResult> listOfSearchResults){
        List<String> listToReturn = new ArrayList<>();
        String textOfArticle;
        for (SearchResult searchResult: listOfSearchResults){
            textOfArticle = searchResult.getTitle() + ": " + searchResult.getSnippet();
            textOfArticle = textOfArticle.replace("<span class=\"searchmatch\">", "")
                    .replace("</span>", "");
            listToReturn.add(textOfArticle);
        }
        return listToReturn;
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        searchView.startWorkingStatus();
        searchModel.searchAllArticleCoincidencesInWikipedia(searchView.getSearchText());
        searchView.stopWorkingStatus();
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        searchView.startWorkingStatus();
        if(!searchView.fullArticleIsSelected()){
            searchModel.searchFirstTermArticleInWikipedia(listOfSearchResults.get(searchView.getSelectedSearchResultIndex()));
        }else{
            searchModel.searchCompleteArticleInWikipedia(listOfSearchResults.get(searchView.getSelectedSearchResultIndex()));
        }
        searchView.setArticleContent(searchModel.getSearchedArticleInWikipedia());
        searchView.stopWorkingStatus();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        int indexOfSelectedSearchResult = searchView.getSelectedSearchResultIndex();
        SearchResult selectedSearchResult;
        if(listOfSearchResults != null && indexOfSelectedSearchResult != -1) {
            selectedSearchResult = listOfSearchResults.get(indexOfSelectedSearchResult);
            storedInfoModel.saveArticle(selectedSearchResult.getTitle(), searchModel.getSearchedArticleInWikipedia());
        } else
            notifyErrorToUser("Search Result Not Selected");
    }

    private void notifyInfoToUser(String message){
        mainView.notifyMessageToUser(message, "Info");
    }

    private void notifyErrorToUser(String message){
        mainView.notifyMessageToUser(message, "Error");
    }

}
