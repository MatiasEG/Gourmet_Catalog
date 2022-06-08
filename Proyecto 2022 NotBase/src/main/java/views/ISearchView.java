package views;

import javax.swing.*;
import java.util.List;

public interface ISearchView {
    String getSearchText();
    int getSelectedSearchResultIndex();
    void setSearchResultsList(List<String> searchResults);
    void setArticleContent(String contentText);
    void setSearchText(String searchText);
    List<String> getSearchResults();
    boolean fullArticleIsSelected();
    JPanel getPanel();
    void startWorkingStatus();
    void stopWorkingStatus();
}
