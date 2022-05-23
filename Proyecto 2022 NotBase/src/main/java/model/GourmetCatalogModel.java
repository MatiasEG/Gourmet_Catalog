package model;

import model.Search.SearchLogic;
import model.Search.SearchResult;

import java.util.List;

public class GourmetCatalogModel implements GourmetCatalogModelInterface{

    private String articleExtract;
    private List<SearchResult> allCoincidences;
    private String articleInWikipedia;


    @Override
    public void deleteArticle(String articleTitle) {
        DataBase.deleteEntry(articleTitle);
    }

    @Override
    public void saveArticle(String articleTitle, String articleExtract) { DataBase.saveInfo(articleTitle, articleExtract); }

    @Override
    public void searchAllArticleCoincidencesInWikipedia(String textToSearch) {
        allCoincidences = JsonParser.searchAllCoincidencesInWikipedia(SearchLogic.executeSearchOfTermInWikipedia(textToSearch));
    }

    @Override
    public List<SearchResult> getAllArticleCoincidencesInWikipedia() {
        return allCoincidences;
    }

    @Override
    public void searchFirstTermArticleInWikipedia(SearchResult searchResult) {
        articleInWikipedia = JsonParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipediaForFirstTerm(searchResult));
    }

    @Override
    public void searchCompleteArticleInWikipedia(SearchResult searchResult){
        articleInWikipedia = JsonParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipediaForEntireArticle(searchResult));
    }

    @Override
    public String getSearchedArticleInWikipedia() {
        return articleInWikipedia;
    }

    @Override
    public void selectStoredArticleExtract(String articleTitle) {
        articleExtract = DataBase.getExtract(articleTitle);
    }

    @Override
    public String getExtractOfSelectedStoredArticle() {
        return articleExtract;
    }

    @Override
    public Object[] getTitlesOfStoredArticles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
