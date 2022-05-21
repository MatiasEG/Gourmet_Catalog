package model;

import com.google.gson.JsonArray;

import java.util.List;

public class GourmetCatalogModel implements GourmetCatalogModelInterface{
    @Override
    public void searchAllCoincidencesInWikipedia(String textToSearch) {
        //TODO
    }

    @Override
    public JsonArray getAllCoincidencesInWikipedia() {
        //TODO
        return null;
    }

    @Override
    public void searchArticleInWikipedia(String pageID) {
        //TODO
    }

    @Override
    public String getArticleInWikipedia() {
        //TODO
        return null;
    }

    @Override
    public void saveArticle(String articleTitle, String articleExtract) {
        //TODO
    }

    @Override
    public List<String> getLocalArticles() {
        //TODO
        return null;
    }

    @Override
    public void selectArticleExtract(String articleTitle) {
        //TODO
    }

    @Override
    public String getExtractOfSelectedArticle() {
        //TODO
        return null;
    }

    @Override
    public void deleteArticle(String articleTitle) {
        //TODO
    }
}
