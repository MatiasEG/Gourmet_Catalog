package presenter;

import model.GourmetCatalogModelInterface;
import views.MainView;
import views.MainViewInterface;

public class GourmetCatalogPresenter implements GourmetCatalogPresenterInterface{
    MainViewInterface mainView;
    GourmetCatalogModelInterface gourmetCatalogModel;

    public GourmetCatalogPresenter(GourmetCatalogModelInterface gourmetCatalogModel){
        this.gourmetCatalogModel = gourmetCatalogModel;
        mainView = new MainView(this);
        mainView.setStoredArticlesTitles(this.gourmetCatalogModel.getTitlesOfStoredArticles());
    }

    @Override
    public void onEventSelectStoredArticle() {
        gourmetCatalogModel.selectStoredArticleExtract(mainView.getSelectedStoredArticleTitle());
        String text = gourmetCatalogModel.getExtractOfSelectedStoredArticle();
        mainView.setStoredArticleContentText(text);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        gourmetCatalogModel.deleteArticle(mainView.getSelectedStoredArticleTitle());
        mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
        mainView.clearStoredArticleView();
    }

    @Override
    public void onEventUpdateStoredArticle() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedStoredArticleTitle(), mainView.getStoredArticleContentText());
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        new Thread(() -> {
            mainView.startWorkingStatus();
            gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(mainView.getSearchText());
            mainView.setSearchResultsList(gourmetCatalogModel.getAllArticleCoincidencesInWikipedia());
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
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.setStoredArticlesTitles(gourmetCatalogModel.getTitlesOfStoredArticles());
    }

    public void showView(){
        mainView.showView();
    }
}
