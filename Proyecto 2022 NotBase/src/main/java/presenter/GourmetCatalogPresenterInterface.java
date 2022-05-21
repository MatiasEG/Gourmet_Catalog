package presenter;

public interface GourmetCatalogPresenterInterface {

//    void onEventSwitchToSearch();
//    void onEventSwitchToStoredInfo();
    void onEventSelectLocalCopy();
    void onEvenDeleteLocalCopy();
    void onEventUpdateLocalCopy();
    void onEventSearch();
    void onEventSelectSearchResult();
    void onEventSaveSearchResult();

    void showView();
}
