package model.storedInfoModel;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;

public interface IStoredInfoModel {

    void addStoredArticlesListener(StoredArticlesListener storedArticlesListener);
    void addLoadArticleListener(LoadArticleListener loadArticleListener);
    void addErrorListener(ErrorListener errorListener);

    void setDataBase(IDataBase database);

    void deleteArticle(String articleTitle);
    void saveArticle(String articleTitle, String articleContent);
    void updateArticle(String articleTitle, String articleContent);

    void loadArticle(String articleTitle);
    String getLoadedArticleContent();

    void loadStoredArticleTitles();
    Object[] getStoredArticleTitles();
}
