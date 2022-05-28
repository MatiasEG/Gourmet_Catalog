package presenter;

import views.MainView;

public interface SearchPresenterInterface {

    void setView(MainView mainView);
    void onEventSearchArticles();
    void onEventSelectArticle();
    void onEventSaveArticle();
}
