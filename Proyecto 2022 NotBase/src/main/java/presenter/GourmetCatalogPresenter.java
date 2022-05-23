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
                mainView.setSearchResultsList(parseListSearchResult(gourmetCatalogModel.getAllArticleCoincidencesInWikipedia()));
                listOfSearchResults = gourmetCatalogModel.getAllArticleCoincidencesInWikipedia();
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
                mainView.notifyMessageToUser("Article Updated", "Info");
            }

            @Override
            public void didSaveArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                mainView.notifyMessageToUser("Article Saved", "Info");
            }

            @Override
            public void didDeleteArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                mainView.notifyMessageToUser("Article Deleted", "Info");
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
    public void onEventSelectStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        if(!storedArticleTitle.equals(""))
            gourmetCatalogModel.selectStoredArticleExtract(storedArticleTitle);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        if(!storedArticleTitle.equals(""))
            gourmetCatalogModel.deleteArticle(storedArticleTitle);
        else
            mainView.notifyMessageToUser("Article Not Selected", "Error");
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        if(!storedArticleTitle.equals(""))
            gourmetCatalogModel.updateArticle(storedArticleTitle, mainView.getStoredArticleContentText());
        else
            mainView.notifyMessageToUser("Article Not Selected", "Error");
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        new Thread(() -> {
            mainView.startWorkingStatus();
            gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(mainView.getSearchText());
            mainView.stopWorkingStatus();
        }).start();
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        new Thread(() -> {
            mainView.startWorkingStatus();
            if(!mainView.completeArticleIsSelected()){
                gourmetCatalogModel.searchFirstTermArticleInWikipedia(listOfSearchResults.get(mainView.getIndexOfSelectedSearchResult()));
            }else{
                gourmetCatalogModel.searchCompleteArticleInWikipedia(listOfSearchResults.get(mainView.getIndexOfSelectedSearchResult()));
            }
            mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
            mainView.stopWorkingStatus();
        }).start();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        int indexOfSelectedSearchResult = mainView.getIndexOfSelectedSearchResult();
        SearchResult selectedSearchResult;
        if(indexOfSelectedSearchResult != -1) {
            selectedSearchResult = listOfSearchResults.get(indexOfSelectedSearchResult);
            gourmetCatalogModel.saveArticle(selectedSearchResult.getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        } else
            mainView.notifyMessageToUser("Search Result Not Selected", "Error");
    }

    public void showView(){
        mainView.showView();
    }
}
