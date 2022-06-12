package views;

public interface IMainView {

    void showView();
    void notifyInfo(String info);
    void notifyError(String error);
    boolean isVisible();
    String getLastError();
    IStoredInfoView getStoredInfoView();
    ISearchView getSearchView();
}
