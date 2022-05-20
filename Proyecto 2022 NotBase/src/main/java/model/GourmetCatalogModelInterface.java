package model;

import com.google.gson.JsonArray;

import java.util.List;

public interface GourmetCatalogModelInterface {

    void searchAllCoincidencesInWikipedia(String textToSearch);

    JsonArray getAllCoincidencesInWikipedia();

    void searchArticleInWikipedia(String pageID);

    String getArticleInWikipedia();

    void saveArticle(String articleTitle, String articleExtract);

    List<String> getLocalArticles();

    void selectArticleExtract(String articleTitle);

    String getExtractOfSelectedArticle();

    void deleteArticle(String articleTitle);
}
