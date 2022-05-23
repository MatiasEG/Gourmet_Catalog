package views;

import model.SearchResult;

import java.util.List;

public interface MainViewInterface {
    String getSearchText();

    SearchResult getSelectedSearchResult();

    String getSelectedStoredArticleTitle();

    // TODO check this name.
    String getStoredArticleContentText();

    void clearStoredArticleView();

    void setStoredArticlesTitles(Object[] localCopies);

    void setStoredArticleContentText(String contentText);

    void setSearchResultsList(List<SearchResult> searchResults);

    void setContentTextOfSearchResult(String contentText);

    void startWorkingStatus();

    void stopWorkingStatus();

    void showView();
}
