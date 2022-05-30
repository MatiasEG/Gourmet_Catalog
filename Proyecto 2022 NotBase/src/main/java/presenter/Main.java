package presenter;

import model.StoredInfoModel.StoredInfoModel;
import model.StoredInfoModel.IStoredInfoModel;
import model.searchModel.SearchModel;
import model.searchModel.SearchModelInterface;
import views.MainView;

public class Main {
    public static void main(String[] args){
        IStoredInfoModel storedInfoModel = new StoredInfoModel();
        SearchModelInterface searchModel = new SearchModel();

        IStoredInfoPresenter storedInfoPresenter = new StoredInfoPresenter(storedInfoModel);
        ISearchPresenter searchPresenter = new SearchPresenter(searchModel, storedInfoModel);

        MainView mainView = new MainView(storedInfoPresenter, searchPresenter);

        storedInfoPresenter.setView(mainView);
        searchPresenter.setView(mainView);

        mainView.showView();
    }
}
