package presenter;

import model.GourmetCatalogModelInterface;
import model.Search.SearchResult;
import model.listeners.SearchListener;
import model.listeners.StoredArticlesListener;
import views.MainView;
import views.MainViewInterface;
import java.util.ArrayList;
import java.util.List;

public class GourmetCatalogPresenter implements GourmetCatalogPresenterInterface{
    MainViewInterface mainView;
    GourmetCatalogModelInterface gourmetCatalogModel;
    List<SearchResult> listOfSearchResults;

    public GourmetCatalogPresenter(GourmetCatalogModelInterface gourmetCatalogModel){
        this.gourmetCatalogModel = gourmetCatalogModel;
        mainView = new MainView(this);
        mainView.setStoredArticlesTitles(this.gourmetCatalogModel.getTitlesOfStoredArticles());
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
                    mainView.setSearchResultsList(parseListSearchResult(articleCoincidences));
                    listOfSearchResults = gourmetCatalogModel.getAllArticleCoincidencesInWikipedia();
                }
            }

            @Override
            public void didFindExtract() {
                mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
            }
        });

        gourmetCatalogModel.addLoadArticleListener(() -> mainView.setStoredArticleContentText(gourmetCatalogModel.getExtractOfSelectedStoredArticle()));

        gourmetCatalogModel.addStoredArticlesListener(new StoredArticlesListener() {
            @Override
            public void didUpdateArticle() {
                notifyInfoToUser("Article Updated");
            }

            @Override
            public void didSaveArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                notifyInfoToUser("Article Saved");
            }

            @Override
            public void didDeleteArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                notifyInfoToUser("Article Deleted");
            }
        });

        gourmetCatalogModel.addErrorListener(errorMessage -> notifyErrorToUser(errorMessage));
    }

    private void notifyInfoToUser(String message){
        mainView.notifyMessageToUser(message, "Info");
    }

    private void notifyErrorToUser(String message){
        mainView.notifyMessageToUser(message, "Error");
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
    public void onEventSelectStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        gourmetCatalogModel.selectStoredArticleExtract(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        gourmetCatalogModel.deleteArticle(storedArticleTitle);
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        String storedArticleContent = mainView.getStoredArticleContentText();
        gourmetCatalogModel.updateArticle(storedArticleTitle, storedArticleContent);
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        mainView.startWorkingStatus();
        gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(mainView.getSearchText());
        mainView.stopWorkingStatus();
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        mainView.startWorkingStatus();
        if(!mainView.completeArticleIsSelected()){
            gourmetCatalogModel.searchFirstTermArticleInWikipedia(listOfSearchResults.get(mainView.getIndexOfSelectedSearchResult()));
        }else{
            gourmetCatalogModel.searchCompleteArticleInWikipedia(listOfSearchResults.get(mainView.getIndexOfSelectedSearchResult()));
        }
        mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.stopWorkingStatus();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        int indexOfSelectedSearchResult = mainView.getIndexOfSelectedSearchResult();
        SearchResult selectedSearchResult;
        if(listOfSearchResults != null && indexOfSelectedSearchResult != -1) {
            selectedSearchResult = listOfSearchResults.get(indexOfSelectedSearchResult);
            gourmetCatalogModel.saveArticle(selectedSearchResult.getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        } else
            notifyErrorToUser("Search Result Not Selected");
    }

    public void showView(){
        mainView.showView();
    }
}
