package presenter;

import model.GourmetCatalogModelInterface;
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
                //TODO notificar usuario
            }

            @Override
            public void didSaveArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                //TODO notificar usuario
            }

            @Override
            public void didDeleteArticle() {
                mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
                mainView.clearStoredArticleView();
                //TODO notificar usuario
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
    }

    @Override
    public void onEventUpdateStoredArticle() {
        String storedArticleTitle = mainView.getSelectedStoredArticleTitle();
        if(!storedArticleTitle.equals(""))
            gourmetCatalogModel.updateArticle(storedArticleTitle, mainView.getStoredArticleContentText());
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
            gourmetCatalogModel.searchArticleInWikipedia(mainView.getSelectedSearchResult());
            mainView.stopWorkingStatus();
        }).start();
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
    }

    public void showView(){
        mainView.showView();
    }
}
