package presenter;

import views.MainView;

public interface SearchPresenterInterface {

    void setUp(MainView mainView);
    void onEventSearchWikipediaArticle();
    void onEventSelectWikipediaArticle();
    void onEventSaveWikipediaArticle();
}
