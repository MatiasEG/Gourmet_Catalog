package presenter;

import views.MainView;

public interface GourmetCatalogSearchViewPresenterInterface {

    void setUp(MainView mainView);
    void onEventSearchWikipediaArticle();
    void onEventSelectWikipediaArticle();
    void onEventSaveWikipediaArticle();
    void showView();
}
