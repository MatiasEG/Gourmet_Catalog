package presenter;

public interface GourmetCatalogPresenterInterface {

    void onEventSelectStoredArticle();
    void onEvenDeleteStoredArticle();
    void onEventUpdateStoredArticle();
    void onEventSearchWikipediaArticle();
    void onEventSelectWikipediaArticle();
    void onEventSaveWikipediaArticle();

    void showView();
}
