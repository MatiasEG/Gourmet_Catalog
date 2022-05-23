package model;

import java.util.List;

public interface GourmetCatalogModelInterface {

    void deleteArticle(String articleTitle);

    void saveArticle(String articleTitle, String articleExtract);

    void searchAllArticleCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllArticleCoincidencesInWikipedia();

    void searchArticleInWikipedia(SearchResult searchResult);
    String getSearchedArticleInWikipedia();

    void selectStoredArticleExtract(String articleTitle);
    String getExtractOfSelectedStoredArticle();

    List<String> getStoredArticles();

    Object[] getTitlesOfStoredArticles();
}
