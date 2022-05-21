package views;

import model.SearchResult;

import java.util.List;

public interface MainViewInterface {
    String getSearchField();

    SearchResult getSelectedSearchResult();

    int getIndexOfSelectedLocalCopy();

    // TODO check this name.
    String getContentTextOfLocalCopy();


    void setListOfLocalCopies(Object[] localCopies);

    void setContentTextOfLocalCopy(String contentText);

    void setListOfSearchResults(List<SearchResult> searchResults);

    void setContentTextOfSearchResult(String contentText);

    void setWorkingStatus();

    void setWaitingStatus();

    void showView();
}
