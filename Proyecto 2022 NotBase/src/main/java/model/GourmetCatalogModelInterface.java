package model;

import model.Search.SearchResult;
import model.listeners.LoadArticleListener;
import model.listeners.SearchListener;
import model.listeners.StoredArticlesListener;

import java.util.List;

public interface GourmetCatalogModelInterface {

    void addStoredArticlesListener(StoredArticlesListener storedArticlesListener);

    void addSearchListener(SearchListener searchListener);

    void addLoadArticleListener(LoadArticleListener loadArticleListener);

    void deleteArticle(String articleTitle);

    void saveArticle(String articleTitle, String articleExtract);

    void updateArticle(String articleTitle, String articleExtract);

    void searchAllArticleCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllArticleCoincidencesInWikipedia();

    void searchFirstTermArticleInWikipedia(SearchResult searchResult);
    void searchCompleteArticleInWikipedia(SearchResult searchResult);
    String getSearchedArticleInWikipedia();

    void selectStoredArticleExtract(String articleTitle);
    String getExtractOfSelectedStoredArticle();

    Object[] getTitlesOfStoredArticles();
}
