package presenter;

import model.GourmetCatalogModelInterface;
import model.Search.SearchResult;
import model.listeners.LoadArticleListener;
import model.listeners.SearchListener;
import model.listeners.StoredArticlesListener;
import views.MainView;
import views.MainViewInterface;

public class GourmetCatalogPresenter implements GourmetCatalogPresenterInterface{
    MainViewInterface mainView;
    GourmetCatalogModelInterface gourmetCatalogModel;

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
                mainView.setSearchResultsList(gourmetCatalogModel.getAllArticleCoincidencesInWikipedia());
            }

            @Override
            public void didFindExtract() {
                mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
            }
        });
        gourmetCatalogModel.addLoadArticleListener(new LoadArticleListener() {
            @Override
            public void didLoadArticle() {
                mainView.setStoredArticleContentText(gourmetCatalogModel.getExtractOfSelectedStoredArticle());
            }
        });
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
                gourmetCatalogModel.searchFirstTermArticleInWikipedia(mainView.getSelectedSearchResult());
            }else{
                gourmetCatalogModel.searchCompleteArticleInWikipedia(mainView.getSelectedSearchResult());
            }
            mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
            mainView.stopWorkingStatus();
        }).start();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        SearchResult selectedSearchResult = mainView.getSelectedSearchResult();
        //TODO que la vista no conozca a SearchRestul
        if(selectedSearchResult != null)
            gourmetCatalogModel.saveArticle(selectedSearchResult.getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        else
            mainView.notifyMessageToUser("Search Result Not Selected", "Error");
    }

    public void showView(){
        mainView.showView();
    }
}
