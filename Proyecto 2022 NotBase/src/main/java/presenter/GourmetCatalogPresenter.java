package presenter;

import dyds.gourmetCatalog.fulllogic.DataBase;
import model.GourmetCatalogModelInterface;
import model.SearchResult;
import views.MainView;
import views.MainViewInterface;

import java.util.ArrayList;
import java.util.List;

public class GourmetCatalogPresenter implements GourmetCatalogPresenterInterface{
    MainViewInterface mainView;
    GourmetCatalogModelInterface gourmetCatalogModel;

    public GourmetCatalogPresenter(GourmetCatalogModelInterface gourmetCatalogModel){
        this.gourmetCatalogModel = gourmetCatalogModel;
        mainView = new MainView(this);
        mainView.setListOfLocalCopies(DataBase.getTitles().stream().sorted().toArray());
    }


//    @Override
//    public void onEventSwitchToSearch() {
//        System.out.println("Ejecutando switch to search");
//        //TODO creo que este metodo no va a ser necesario
//    }
//
//    @Override
//    public void onEventSwitchToStoredInfo() {
//        System.out.println("Ejecutando switch to stored info");
//        //TODO creo que este metodo no va a ser necesario
//    }

    @Override
    public void onEventSelectLocalCopy() {
        mainView.setContentTextOfLocalCopy("Selecciono el item con titulo: " + mainView.getTitleOfSelectedLocalCopy());
        gourmetCatalogModel.selectArticleExtract(mainView.getTitleOfSelectedLocalCopy());
        String text = gourmetCatalogModel.getExtractOfSelectedArticle();
        mainView.setContentTextOfLocalCopy(text);
        //TODO ok?
    }

    @Override
    public void onEvenDeleteLocalCopy() {
        System.out.println("Ejecutando delete local copy");
        gourmetCatalogModel.deleteArticle(mainView.getTitleOfSelectedLocalCopy());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getLocalArticles().toArray(new String[0]));
        mainView.cleanViewForLocalArticles();
        //TODO ok?
    }

    @Override
    public void onEventUpdateLocalCopy() {
        System.out.println("Ejecutando update local copy");
        gourmetCatalogModel.saveArticle(mainView.getTitleOfSelectedLocalCopy(), mainView.getContentTextOfLocalCopy());
        //TODO ok?
    }

    @Override
    public void onEventSearch() {
//        List<SearchResult> searchResults = new ArrayList<>();
//        String searchText = mainView.getSearchField();
//        searchResults.add(new SearchResult(searchText+"1", "1", searchText+"11"));
//        searchResults.add(new SearchResult(searchText+"2", "2", searchText+"22"));
//        searchResults.add(new SearchResult(searchText+"3", "3", searchText+"33"));
//        mainView.setListOfSearchResults(searchResults);
        //TODO ok?

        gourmetCatalogModel.searchAllCoincidencesInWikipedia(mainView.getSearchField());
        // El modelo deberia convertir el JsonArray en un List<String> ?
        mainView.setListOfSearchResults(gourmetCatalogModel.getAllCoincidencesInWikipedia());
    }

    @Override
    public void onEventSelectSearchResult() {
        gourmetCatalogModel.searchArticleInWikipedia(mainView.getSelectedSearchResult());
        mainView.setContentTextOfSearchResult(gourmetCatalogModel.getArticleInWikipedia());
//        mainView.setContentTextOfSearchResult(mainView.getSelectedSearchResult().getTitle() + ": " + mainView.getSelectedSearchResult().getSnippet());
        //TODO ok?
    }

    @Override
    public void onEventSaveSearchResult() {
        System.out.println("Ejecutando save search result");
        gourmetCatalogModel.saveArticle(mainView.getSelectedSearchResult().getTitle(), gourmetCatalogModel.getArticleInWikipedia());
        mainView.setListOfLocalCopies(gourmetCatalogModel.getLocalArticles().toArray(new String[0]));
        //TODO
    }

    public void showView(){
        mainView.showView();
    }
}
