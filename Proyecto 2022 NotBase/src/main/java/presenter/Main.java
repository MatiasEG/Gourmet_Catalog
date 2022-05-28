package presenter;

import model.BDmodel.GourmetCatalogStoredInfoModel;
import model.BDmodel.GourmetCatalogStoredInfoModelInterface;
import model.searchModel.GourmetCatalogSearchModel;
import model.searchModel.GourmetCatalogSearchModelInterface;
import views.MainView;

public class Main {
    public static void main(String[] args){
        GourmetCatalogStoredInfoModelInterface gourmetCatalogModel = new GourmetCatalogStoredInfoModel();
        GourmetCatalogSearchModelInterface gourmetCatalogSearchModel = new GourmetCatalogSearchModel();

        GourmetCatalogStoredInfoViewPresenterInterface storedInfoViewPresenter = new GourmetCatalogStoredInfoViewPresenter(gourmetCatalogModel);
        GourmetCatalogSearchViewPresenterInterface searchViewPresenter = new GourmetCatalogSearchViewPresenter(gourmetCatalogSearchModel, gourmetCatalogModel);

        MainView mainView = new MainView(storedInfoViewPresenter, searchViewPresenter);

        storedInfoViewPresenter.setUp(mainView);
        searchViewPresenter.setUp(mainView);

        storedInfoViewPresenter.showView();
        searchViewPresenter.showView();

        mainView.showView();
    }
}
