package views;

import java.util.List;

public interface MainViewInterface {





    String getSearchField();

    int getIndexOfSelectedSearchResult();

    int getIndexOfSelectedLocalCopy();

    // TODO check this name.
    String getContentTextOfLocalCopy();


    void setListOfLocalCopies(List<String> localCopies);

    void setContentTextOfLocalCopy(String contentText);

    void setListOfSearchResults(List<String> searchResults);

    void setContentTextOfSearchResult(String contentText);

    void showView();
}
