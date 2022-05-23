package views;

import model.SearchResult;

import java.util.List;

public interface MainViewInterface {
    String getSearchField();

    SearchResult getSelectedSearchResult();

//    int getIndexOfSelectedLocalCopy();

    String getTitleOfSelectedLocalCopy();

    // TODO check this name.
    String getContentTextOfLocalCopy();

    void cleanViewForLocalArticles();

    void setListOfLocalCopies(Object[] localCopies);

    void setContentTextOfLocalCopy(String contentText);

    void setListOfSearchResults(List<SearchResult> searchResults);

    void setContentTextOfSearchResult(String contentText);

    void setWorkingStatus();

    void setWaitingStatus();

    void showView();
}
