package views;

public interface StoredInfoViewInterface {

    String getSelectedStoredArticleTitle();
    String getStoredArticleContentText();
    void clearStoredArticleView();
    void setStoredArticlesTitles(Object[] localCopies);
    void setStoredArticleContentText(String contentText);
}
