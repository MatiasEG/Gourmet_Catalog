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
        mainView.setStoredArticlesTitles(gourmetCatalogModel.getStoredArticles().toArray(new String[0]));
        mainView.clearStoredArticleView();
    }

    @Override
    public void onEventUpdateStoredArticle() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedStoredArticleTitle(), mainView.getStoredArticleContentText());
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(mainView.getSearchText());
        mainView.setSearchResultsList(gourmetCatalogModel.getAllArticleCoincidencesInWikipedia());
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        gourmetCatalogModel.searchArticleInWikipedia(mainView.getSelectedSearchResult());
        mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.setStoredArticlesTitles(gourmetCatalogModel.getStoredArticles().toArray(new String[0]));
    }

    public void showView(){
        mainView.showView();
    }
}
