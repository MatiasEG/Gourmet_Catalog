package views;

import java.util.List;

public interface SearchViewInterface {
    String getSearchText();
    int getIndexOfSelectedSearchResult();
    void setSearchResultsList(List<String> searchResults);
    void setContentTextOfSearchResult(String contentText);
    boolean completeArticleIsSelected();
    void startWorkingStatus();
    void stopWorkingStatus();
    void showView();
}
