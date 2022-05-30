package presenter;

import model.StoredInfoModel.StoredInfoModel;
import model.StoredInfoModel.StoredInfoModelInterface;
import model.searchModel.SearchModel;
import model.searchModel.ISearchModel;
import views.MainView;

public class Main {
    public static void main(String[] args){
        StoredInfoModelInterface storedInfoModel = new StoredInfoModel();
        ISearchModel searchModel = new SearchModel();

        StoredInfoPresenterInterface storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);
        SearchPresenterInterface searchPresenter = new SearchPresenter(searchModel, storedInfoModel);

        MainView mainView = new MainView(storedInfoPresenter, searchPresenter);

        storedInfoPresenter.setView(mainView);
        searchPresenter.setView(mainView);

        mainView.showView();
    }
}
