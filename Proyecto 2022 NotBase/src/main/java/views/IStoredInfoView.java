package views;

import javax.swing.*;

public interface IStoredInfoView {

    void setStoredArticlesTitles(Object[] localCopies);
    void setSelectedArticleTitle(String articleTitle);
    void setArticleContent(String contentText);
    JPanel getPanel();
    String getSelectedArticleTitle();
    String getArticleContent();
}
