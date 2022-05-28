package presenter;

import views.MainView;

public interface StoredInfoPresenterInterface {

    void setView(MainView mainView);
    void onEventSelectArticle();
    void onEvenDeleteArticle();
    void onEventUpdateArticle();
}
