package model;

import com.google.gson.JsonArray;
import dyds.gourmetCatalog.fulllogic.DataBase;

import java.util.List;

public class GourmetCatalogModel implements GourmetCatalogModelInterface{

    private String articleExtract;
    private JsonArray allCoincidences;
    private String articleInWikipedia;


    @Override
    public void searchAllCoincidencesInWikipedia(String textToSearch) {
        allCoincidences = JsonParser.searchAllCoincidencesInWikipedia(textToSearch);
    }

    @Override
    public JsonArray getAllCoincidencesInWikipedia() {
        return allCoincidences;
    }

    @Override
    public void searchArticleInWikipedia(String pageID) {
        articleInWikipedia = JsonParser.searchArticleInWikipedia(pageID);
    }

    @Override
    public String getArticleInWikipedia() {
        return articleInWikipedia;
    }

    @Override
    public void saveArticle(String articleTitle, String articleExtract) {
        DataBase.saveInfo(articleTitle, articleExtract);
    }

    @Override
    public List<String> getLocalArticles() {
        return DataBase.getTitles();
    }

    @Override
    public void selectArticleExtract(String articleTitle) {
        articleExtract = DataBase.getExtract(articleTitle);
    }

    @Override
    public String getExtractOfSelectedArticle() {
        return articleExtract;
    }

    @Override
    public void deleteArticle(String articleTitle) {
        DataBase.deleteEntry(articleTitle);
    }
}
