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
        mainView.setListOfLocalCopies(this.gourmetCatalogModel.getTitlesOfLocalArticles());
    }

    @Override
    public void onEventSelectLocalCopy() {
        gourmetCatalogModel.selectArticleExtract(mainView.getTitleOfSelectedLocalCopy());
        String text = gourmetCatalogModel.getExtractOfSelectedArticle();
        mainView.setContentTextOfLocalCopy(text);
    }

    @Override
    public void onEvenDeleteLocalCopy() {
        gourmetCatalogModel.deleteArticle(mainView.getTitleOfSelectedLocalCopy());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getLocalArticles().toArray(new String[0]));
        mainView.cleanViewForLocalArticles();
    }

    @Override
    public void onEventUpdateLocalCopy() {
        gourmetCatalogModel.saveArticle(mainView.getTitleOfSelectedLocalCopy(), mainView.getContentTextOfLocalCopy());
    }

    @Override
    public void onEventSearch() {
        gourmetCatalogModel.searchAllCoincidencesInWikipedia(mainView.getSearchField());
        mainView.setListOfSearchResults(gourmetCatalogModel.getAllCoincidencesInWikipedia());
    }

    @Override
    public void onEventSelectSearchResult() {
        gourmetCatalogModel.searchArticleInWikipedia(mainView.getSelectedSearchResult());
        mainView.setContentTextOfSearchResult(gourmetCatalogModel.getSearchedArticleInWikipedia());
    }

    @Override
    public void onEventSaveSearchResult() {
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getSearchedArticleInWikipedia());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getLocalArticles().toArray(new String[0]));
    }

    public void showView(){
        mainView.showView();
    }
}
