package views;

public interface IMainView {

    void notifyMessageToUser(String msg, String title);
    void showView();
    IStoredInfoView getStoredInfoView();
    ISearchView getSearchView();
}
