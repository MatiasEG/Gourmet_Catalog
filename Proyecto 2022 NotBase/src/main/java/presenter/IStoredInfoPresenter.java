package presenter;

import views.MainView;

public interface IStoredInfoPresenter {

    void setView(MainView mainView);
    void onEventSelectArticle();
    void onEvenDeleteArticle();
    void onEventUpdateArticle();
}
