package presenter;

import model.GourmetCatalogModelInterface;
import model.Search.SearchResult;
import model.listeners.SearchListener;
import views.MainView;
import views.MainViewInterface;
import views.SearchView;

import java.util.ArrayList;
import java.util.List;

public class GourmetCatalogSearchViewPresenter implements GourmetCatalogSearchViewPresenterInterface {
    MainViewInterface mainView;
    SearchView searchView;
    GourmetCatalogModelInterface gourmetCatalogModel;
    List<SearchResult> listOfSearchResults;

    public GourmetCatalogSearchViewPresenter(GourmetCatalogModelInterface gourmetCatalogModel){
        this.gourmetCatalogModel = gourmetCatalogModel;
//        this.mainView = mainView;
//        this.searchView = mainView.getSearchView();
//        initListeners();
    }

    @Override
    public void setUp(MainView mainView){
        this.mainView = mainView;
        this.searchView = mainView.getSearchView();
        initListeners();
    }

    private void initListeners(){
        gourmetCatalogModel.addSearchListener(new SearchListener() {
            @Override
            public void didFindArticles() {
                List<SearchResult> articleCoincidences = gourmetCatalogModel.getAllArticleCoincidencesInWikipedia();
                if(articleCoincidences.isEmpty())
                    notifyInfoToUser("No Coincidences Found");
                else {
                    searchView.setSearchResultsList(parseListSearchResult(articleCoincidences));
                    listOfSearchResults = gourmetCatalogModel.getAllArticleCoincidencesInWikipedia();
                }
            }

            @Override
            public void didFindArticleContent() {
                searchView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
            }
        });
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
        gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(searchView.getSearchText());
        searchView.stopWorkingStatus();
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        searchView.startWorkingStatus();
        if(!searchView.completeArticleIsSelected()){
            gourmetCatalogModel.searchFirstTermArticleInWikipedia(listOfSearchResults.get(searchView.getIndexOfSelectedSearchResult()));
        }else{
            gourmetCatalogModel.searchCompleteArticleInWikipedia(listOfSearchResults.get(searchView.getIndexOfSelectedSearchResult()));
        }
        mainView.getSearchView().setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.getSearchView().stopWorkingStatus();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        int indexOfSelectedSearchResult = searchView.getIndexOfSelectedSearchResult();
        SearchResult selectedSearchResult;
        if(listOfSearchResults != null && indexOfSelectedSearchResult != -1) {
            selectedSearchResult = listOfSearchResults.get(indexOfSelectedSearchResult);
            gourmetCatalogModel.saveArticle(selectedSearchResult.getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        } else
            notifyErrorToUser("Search Result Not Selected");
    }

    public void showView(){ searchView.showView(); }

    private void notifyInfoToUser(String message){
        mainView.notifyMessageToUser(message, "Info");
    }

    private void notifyErrorToUser(String message){
        mainView.notifyMessageToUser(message, "Error");
    }

}
