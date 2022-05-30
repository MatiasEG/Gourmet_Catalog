package presenter;

import views.MainView;

public interface ISearchPresenter {

    void setView(MainView mainView);
    void onEventSearchArticles();
    void onEventSelectArticle();
    void onEventSaveArticle();
}
