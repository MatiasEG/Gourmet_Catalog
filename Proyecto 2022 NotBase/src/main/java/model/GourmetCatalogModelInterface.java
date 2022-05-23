package model;

import java.util.List;

public interface GourmetCatalogModelInterface {

    void deleteArticle(String articleTitle);

    void saveArticle(String articleTitle, String articleExtract);

    void searchAllCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllCoincidencesInWikipedia();

    void searchArticleInWikipedia(SearchResult searchResult);
    String getSearchedArticleInWikipedia();

    void selectArticleExtract(String articleTitle);
    String getExtractOfSelectedArticle();

    List<String> getLocalArticles();

    Object[] getTitlesOfLocalArticles();
}
