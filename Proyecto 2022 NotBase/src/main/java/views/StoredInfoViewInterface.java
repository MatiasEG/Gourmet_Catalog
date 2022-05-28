package views;

import javax.swing.*;

public interface StoredInfoViewInterface {

    String getSelectedStoredArticleTitle();
    String getStoredArticleContentText();
    void clearStoredArticleView();
    void setStoredArticlesTitles(Object[] localCopies);
    void setStoredArticleContentText(String contentText);
    JPanel getPanel();
}
