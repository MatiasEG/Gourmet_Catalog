package presenter;

import views.IMainView;

public interface IStoredInfoPresenter {

    void setView(IMainView mainView);
    void onEventSelectArticle();
    void onEvenDeleteArticle();
    void onEventUpdateArticle();
    boolean isActivelyWorking();
}
