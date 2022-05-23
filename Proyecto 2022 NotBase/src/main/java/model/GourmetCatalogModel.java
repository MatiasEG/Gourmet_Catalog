package model;

import java.util.List;

public class GourmetCatalogModel implements GourmetCatalogModelInterface{

    private String articleExtract;
    private List<SearchResult> allCoincidences;
    private String articleInWikipedia;


    @Override
    public void searchAllCoincidencesInWikipedia(String textToSearch) {
        allCoincidences = JsonParser.searchAllCoincidencesInWikipedia(textToSearch);
    }

    @Override
    public List<SearchResult> getAllCoincidencesInWikipedia() {
        return allCoincidences;
    }

    @Override
    public void searchArticleInWikipedia(SearchResult searchResult) {
        articleInWikipedia = JsonParser.searchArticleInWikipedia(searchResult);
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
