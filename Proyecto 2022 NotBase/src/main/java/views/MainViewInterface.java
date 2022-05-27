package views;

public interface MainViewInterface {

    void notifyMessageToUser(String msg, String title);
    void showView();
    StoredInfoView getStoredInfoView();
    SearchView getSearchView();
}
