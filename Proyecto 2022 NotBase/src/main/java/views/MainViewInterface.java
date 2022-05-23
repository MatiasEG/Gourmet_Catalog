package views;

import java.util.List;

public interface MainViewInterface {
    String getSearchText();

    int getIndexOfSelectedSearchResult();

    String getSelectedStoredArticleTitle();

    // TODO check this name.
    String getStoredArticleContentText();

    void clearStoredArticleView();

    void setStoredArticlesTitles(Object[] localCopies);

    void setStoredArticleContentText(String contentText);

    void setSearchResultsList(List<String> searchResults);

    void notifyMessageToUser(String msg, String title);

    void setContentTextOfSearchResult(String contentText);

    boolean completeArticleIsSelected();

    void startWorkingStatus();

    void stopWorkingStatus();

    void showView();
}
