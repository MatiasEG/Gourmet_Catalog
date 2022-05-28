package views;

import javax.swing.*;

public interface StoredInfoViewInterface {

    String getSelectedArticleTitle();
    String getArticleContent();
    void clearView();
    void setStoredArticlesTitles(Object[] localCopies);
    void setArticleContent(String contentText);
    JPanel getPanel();
}
