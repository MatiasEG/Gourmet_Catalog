package views;

import presenter.GourmetCatalogPresenterInterface;

import java.util.List;

public class MainView implements  MainViewInterface{
    private GourmetCatalogPresenterInterface gourmetCatalogPresenter;

    public MainView(GourmetCatalogPresenterInterface gourmetCatalogPresenter){
        this.gourmetCatalogPresenter = gourmetCatalogPresenter;
    }

    @Override
    public String getSearchField() {
        //TODO
        return null;
    }

    @Override
    public int getIndexOfSelectedSearchResult() {
        //TODO
        return 0;
    }

    @Override
    public int getIndexOfSelectedLocalCopy() {
        //TODO
        return 0;
    }

    @Override
    public String getContentTextOfLocalCopy() {
        //TODO
        return null;
    }

    @Override
    public void setListOfLocalCopies(List<String> localCopies) {
        //TODO
    }

    @Override
    public void setContentTextOfLocalCopy(String contentText) {
        //TODO
    }

    @Override
    public void setListOfSearchResults(List<String> searchResults) {
        //TODO
    }

    @Override
    public void setContentTextOfSearchResult(String contentText) {
        //TODO
    }

    @Override
    public void showView(){
        System.out.println("Mostrar pantalla");
        //TODO
    }
}
