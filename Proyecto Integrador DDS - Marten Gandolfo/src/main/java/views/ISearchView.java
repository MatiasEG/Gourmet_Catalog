package views;

import javax.swing.*;
import java.util.List;

public interface ISearchView {

    void setSearchResultsList(List<String> searchResults);
    void setArticleContent(String contentText);
    void setSearchText(String searchText);
    void setSelectedSearchResultIndex(int selectedSearchResultIndex);

    JPanel getPanel();
    String getSearchText();
    int getSelectedSearchResultIndex();
    String getArticleContent();
    List<String> getSearchResults();

    void selectFullArticleOption();
    void selectArticleSummaryOption();
    boolean fullArticleIsSelected();

    void startWorkingStatus();
    void stopWorkingStatus();
}
