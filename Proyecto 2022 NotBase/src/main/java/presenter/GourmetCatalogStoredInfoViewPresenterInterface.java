package presenter;

import views.MainView;

public interface GourmetCatalogStoredInfoViewPresenterInterface {

    void setUp(MainView mainView);
    void onEventSelectStoredArticle();
    void onEvenDeleteStoredArticle();
    void onEventUpdateStoredArticle();
    void showView();
}
