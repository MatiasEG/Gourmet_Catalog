package model;

import model.Search.SearchResult;

import java.util.List;

public interface GourmetCatalogModelInterface {

    void deleteArticle(String articleTitle);

    void saveArticle(String articleTitle, String articleExtract);

    void searchAllArticleCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllArticleCoincidencesInWikipedia();

    void searchFirstTermArticleInWikipedia(SearchResult searchResult);
    void searchCompleteArticleInWikipedia(SearchResult searchResult);
    String getSearchedArticleInWikipedia();

    void selectStoredArticleExtract(String articleTitle);
    String getExtractOfSelectedStoredArticle();

    Object[] getTitlesOfStoredArticles();
}
