package presenter;

import model.storedInfoModel.IStoredInfoModel;
import model.searchModel.searchLogic.SearchResult;
import model.listeners.SearchListener;
import model.searchModel.ISearchModel;
import views.IMainView;
import views.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements ISearchPresenter {
    private IMainView mainView;
    private ISearchView searchView;
    private ISearchModel searchModel;
    private IStoredInfoModel storedInfoModel;
    private List<SearchResult> searchResultsList;
    private SearchResult selectedSearchResult;
    private Thread thread;

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
                    mainView.notifyInfo("No Coincidences Found");
                else
                    searchView.setSearchResultsList(formatSearchResultsList(searchResultsList));
            }
            public void didFindArticleContent() {
                String articleContent = searchModel.getFoundArticleContent();
                searchView.setArticleContent(articleContent);
            }
        });

        searchModel.addErrorListener(errorMessage -> mainView.notifyError(errorMessage));
    }

    private List<String> formatSearchResultsList(List<SearchResult> searchResultsList){
        List<String> searchResultsAsStrings = new ArrayList<>();
        for (SearchResult searchResult: searchResultsList){
            String searchResultString = searchResult.getTitle() + ": " + searchResult.getSnippet();
            searchResultString = searchResultString.replace("<span class=\"searchmatch\">", "")
                    .replace("</span>", "");
            searchResultsAsStrings.add(searchResultString);
        }
        return searchResultsAsStrings;
    }

    @Override
    public void onEventSearchArticles() {
        thread = new Thread(() -> {
            String textToSearch = searchView.getSearchText();
            searchModel.searchAllCoincidencesInWikipedia(textToSearch);
        });
        thread.start();
    }

    @Override
    public void onEventSelectArticle() {
        thread = new Thread(() -> {
            selectedSearchResult = searchResultsList.get(searchView.getSelectedSearchResultIndex());
            if(searchView.fullArticleIsSelected()){
                searchModel.searchFullArticleInWikipedia(selectedSearchResult);
            }else{
                searchModel.searchArticleSummaryInWikipedia(selectedSearchResult);
            }
        });
        thread.start();
    }

    @Override
    public void onEventSaveArticle() {
        thread = new Thread(() -> {
            if(selectedSearchResult != null) {
                String articleTitle = selectedSearchResult.getTitle();
                String articleContent = searchModel.getFoundArticleContent();
                storedInfoModel.saveArticle(articleTitle, articleContent);
            } else
                mainView.notifyError("Search Result Not Selected");
        });
        thread.start();
    }

    public void setSearchResultsList(List<SearchResult> searchResultsList){
        this.searchResultsList = searchResultsList;
    }

    public void setSelectedSearchResult(SearchResult selectedSearchResult){
        this.selectedSearchResult = selectedSearchResult;
    }

    @Override
    public boolean isActivelyWorking(){
        return thread != null && thread.isAlive();
    }
}
