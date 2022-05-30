package views;

import javax.swing.*;

public interface IStoredInfoView {

    String getSelectedArticleTitle();
    String getArticleContent();
    void setStoredArticlesTitles(Object[] localCopies);
    void setArticleContent(String contentText);
    JPanel getPanel();
}
