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
    }


    @Override
    public void onEventSwitchToSearch() {
        //TODO
    }

    @Override
    public void onEventSwitchToStoredInfo() {
        //TODO
    }

    @Override
    public void onEventSelectLocalCopy() {
        //TODO
    }

    @Override
    public void onEvenDeleteLocalCopy() {
        //TODO
    }

    @Override
    public void onEventUpdateLocalCopy() {
        //TODO
    }

    @Override
    public void onEventSearch() {
        //TODO
    }

    @Override
    public void onEventSelectSearchResult() {
        //TODO
    }

    @Override
    public void onEventSaveSearchResult() {
        //TODO
    }

    public void showView(){
        mainView.showView();
    }
}
