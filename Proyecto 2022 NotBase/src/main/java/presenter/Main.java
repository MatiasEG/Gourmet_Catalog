package presenter;

import model.GourmetCatalogModel;
import model.GourmetCatalogModelInterface;
import views.MainView;

public class Main {
    public static void main(String[] args){
        GourmetCatalogModelInterface gourmetCatalogModel = new GourmetCatalogModel();

        GourmetCatalogStoredInfoViewPresenterInterface storedInfoViewPresenter = new GourmetCatalogStoredInfoViewPresenter(gourmetCatalogModel);
        GourmetCatalogSearchViewPresenterInterface searchViewPresenter = new GourmetCatalogSearchViewPresenter(gourmetCatalogModel);

        MainView mainView = new MainView(storedInfoViewPresenter, searchViewPresenter);

        storedInfoViewPresenter.setUp(mainView);
        searchViewPresenter.setUp(mainView);

        storedInfoViewPresenter.showView();
        searchViewPresenter.showView();

        mainView.showView();
    }
}
