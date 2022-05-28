package presenter;

import model.StoredInfoModel.StoredInfoModel;
import model.StoredInfoModel.StoredInfoModelInterface;
import model.searchModel.SearchModel;
import model.searchModel.SearchModelInterface;
import views.MainView;

public class Main {
    public static void main(String[] args){
        StoredInfoModelInterface storedInfoModel = new StoredInfoModel();
        SearchModelInterface searchModel = new SearchModel();

        StoredInfoPresenterInterface storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);
        SearchPresenterInterface searchPresenter = new SearchPresenter(searchModel, storedInfoModel);

        MainView mainView = new MainView(storedInfoPresenter, searchPresenter);

        storedInfoPresenter.setUp(mainView);
        searchPresenter.setUp(mainView);

        storedInfoPresenter.showView();
        searchPresenter.showView();

        mainView.showView();
    }
}
