package presenter;

import model.storedInfoModel.StoredInfoModel;
import model.storedInfoModel.IStoredInfoModel;
import model.searchModel.SearchModel;
import model.searchModel.ISearchModel;
import views.MainView;

public class Main {
    public static void main(String[] args){
        IStoredInfoModel storedInfoModel = new StoredInfoModel();
        ISearchModel searchModel = new SearchModel();

        IStoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);
        ISearchPresenter searchPresenter = new SearchPresenter(searchModel, storedInfoModel);

        MainView mainView = new MainView(storedInfoPresenter, searchPresenter);

        storedInfoPresenter.setView(mainView);
        searchPresenter.setView(mainView);

        mainView.showView();
    }
}
