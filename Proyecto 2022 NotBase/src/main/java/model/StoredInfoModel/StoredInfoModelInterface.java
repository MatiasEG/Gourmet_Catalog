package model.StoredInfoModel;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.StoredArticlesListener;

public interface StoredInfoModelInterface {

    void addStoredArticlesListener(StoredArticlesListener storedArticlesListener);

    void addLoadArticleListener(LoadArticleListener loadArticleListener);

    void addErrorListener(ErrorListener errorListener);

    void deleteArticle(String articleTitle);

    void saveArticle(String articleTitle, String articleExtract);

    void updateArticle(String articleTitle, String articleExtract);

    void selectStoredArticle(String articleTitle);
    String getSelectedStoredArticleContent();

    Object[] getTitlesOfStoredArticles();
}
