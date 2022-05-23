package presenter;

public interface GourmetCatalogPresenterInterface {

    void onEventSelectLocalCopy();
    void onEvenDeleteLocalCopy();
    void onEventUpdateLocalCopy();
    void onEventSearch();
    void onEventSelectSearchResult();
    void onEventSaveSearchResult();

    void showView();
}
