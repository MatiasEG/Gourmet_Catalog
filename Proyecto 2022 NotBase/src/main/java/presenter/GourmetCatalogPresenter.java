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
        mainView.setListOfLocalCopies(this.gourmetCatalogModel.getTitlesOfStoredArticles());
    }

    @Override
    public void onEventSelectStoredArticle() {
        gourmetCatalogModel.selectStoredArticleExtract(mainView.getTitleOfSelectedLocalCopy());
        String text = gourmetCatalogModel.getExtractOfSelectedStoredArticle();
        mainView.setContentTextOfLocalCopy(text);
    }

    @Override
    public void onEvenDeleteStoredArticle() {
        gourmetCatalogModel.deleteArticle(mainView.getTitleOfSelectedLocalCopy());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getStoredArticles().toArray(new String[0]));
        mainView.cleanViewForLocalArticles();
    }

    @Override
    public void onEventUpdateStoredArticle() {
        gourmetCatalogModel.saveArticle(mainView.getTitleOfSelectedLocalCopy(), mainView.getContentTextOfLocalCopy());
    }

    @Override
    public void onEventSearchWikipediaArticle() {
        gourmetCatalogModel.searchAllArticleCoincidencesInWikipedia(mainView.getSearchField());
        mainView.setListOfSearchResults(gourmetCatalogModel.getAllArticleCoincidencesInWikipedia());
    }

    @Override
    public void onEventSelectWikipediaArticle() {
        gourmetCatalogModel.searchArticleInWikipedia(mainView.getSelectedSearchResult());
        mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
    }

    @Override
    public void onEventSaveWikipediaArticle() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getStoredArticles().toArray(new String[0]));
    }

    public void showView(){
        mainView.showView();
    }
}
