package presenter;

import views.MainView;

public interface StoredInfoPresenterInterface {

    void setUp(MainView mainView);
    void onEventSelectStoredArticle();
    void onEvenDeleteStoredArticle();
    void onEventUpdateStoredArticle();
}
