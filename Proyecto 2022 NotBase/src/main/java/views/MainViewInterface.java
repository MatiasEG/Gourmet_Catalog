package views;

public interface MainViewInterface {

    void notifyMessageToUser(String msg, String title);
    void showView();
    StoredInfoViewInterface getStoredInfoView();
    SearchViewInterface getSearchView();
}
