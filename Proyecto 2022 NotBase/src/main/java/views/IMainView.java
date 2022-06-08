package views;

public interface IMainView {

    void notifyInfo(String info);
    void notifyError(String error);
    String getLastError();
    void showView();
    IStoredInfoView getStoredInfoView();
    ISearchView getSearchView();
}
