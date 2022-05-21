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


    @Override
    public void onEventSwitchToSearch() {
        System.out.println("Ejecutando switch to search");
        //TODO creo que este metodo no va a ser necesario
    }

    @Override
    public void onEventSwitchToStoredInfo() {
        System.out.println("Ejecutando switch to stored info");
        //TODO creo que este metodo no va a ser necesario
    }

    @Override
    public void onEventSelectLocalCopy() {
        mainView.setContentTextOfLocalCopy("Selecciono el item " + mainView.getIndexOfSelectedLocalCopy());
        //TODO
    }

    @Override
    public void onEvenDeleteLocalCopy() {
        System.out.println("Ejecutando delete local copy");
        //TODO
    }

    @Override
    public void onEventUpdateLocalCopy() {
        System.out.println("Ejecutando update local copy");
        //TODO
    }

    @Override
    public void onEventSearch() {
        List<SearchResult> searchResults = new ArrayList<>();
        String searchText = mainView.getSearchField();
        searchResults.add(new SearchResult(searchText+"1", "1", searchText+"11"));
        searchResults.add(new SearchResult(searchText+"2", "2", searchText+"22"));
        searchResults.add(new SearchResult(searchText+"3", "3", searchText+"33"));
        mainView.setListOfSearchResults(searchResults);
        //TODO
    }

    @Override
    public void onEventSelectSearchResult() {
        mainView.setContentTextOfSearchResult(mainView.getSelectedSearchResult().getTitle() + ": " + mainView.getSelectedSearchResult().getSnippet());
        //TODO
    }

    @Override
    public void onEventSaveSearchResult() {
        System.out.println("Ejecutando save search result");
        //TODO
    }

    public void showView(){
        mainView.showView();
    }
}
