package model;

import model.Search.SearchLogic;
import model.Search.SearchResult;

import model.listeners.ErrorListener;
import model.listeners.LoadArticleListener;
import model.listeners.SearchListener;
import model.listeners.StoredArticlesListener;

import java.util.ArrayList;
import java.util.List;

public class GourmetCatalogModel implements GourmetCatalogModelInterface{

    private String articleContent;
    private List<SearchResult> allCoincidences;
    private String articleInWikipedia;
    private List<LoadArticleListener> loadArticleListeners = new ArrayList<>();
    private List<SearchListener> searchListeners = new ArrayList<>();
    private List<StoredArticlesListener> storedArticlesListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();

    public GourmetCatalogModel(){
        DataBase.loadDatabase();
    }


    @Override
    public void addStoredArticlesListener(StoredArticlesListener storedArticlesListener) {
        storedArticlesListeners.add(storedArticlesListener);
    }

    @Override
    public void addSearchListener(SearchListener searchListener) {
        searchListeners.add(searchListener);
    }

    @Override
    public void addLoadArticleListener(LoadArticleListener loadArticleListener) {
        loadArticleListeners.add(loadArticleListener);
    }

    @Override
    public void addErrorListener(ErrorListener errorListener){
        errorListeners.add(errorListener);
    }

    private void notifyUpdateArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didUpdateArticle();
    }

    private void notifySaveArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didSaveArticle();
    }

    private void notifyDeleteArticle(){
        for(StoredArticlesListener storedArticlesListener : storedArticlesListeners)
            storedArticlesListener.didDeleteArticle();
    }

    private void notifyFoundArticles(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticles();
    }

    private void notifyFoundArticleContent(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticleContent();
    }

    private void notifyLoadArticle(){
        for(LoadArticleListener loadArticleListener : loadArticleListeners)
            loadArticleListener.didLoadArticle();
    }

    private void notifyErrorOccurred(String errorMessage){
        for(ErrorListener errorListener : errorListeners)
            errorListener.didErrorOccurred(errorMessage);
    }

    @Override
    public void deleteArticle(String articleTitle) {

        System.out.println(" --------------- delete");
        System.out.println(articleTitle);
        System.out.println(" --------------- delete");

        if(isValidString(articleTitle)) {
            DataBase.deleteEntry(articleTitle);
            notifyDeleteArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    private boolean isValidString(String string){
        return string != null && !string.equals("");
    }

    @Override
    public void saveArticle(String articleTitle, String articleContent) {
        DataBase.saveInfo(articleTitle, articleContent);
        notifySaveArticle();
    }

    @Override
    public void updateArticle(String articleTitle, String articleContent) {

        System.out.println(" --------------- update");
        System.out.println(articleTitle);
        System.out.println(" --------------- update");

        if(isValidString(articleTitle)) {
            DataBase.saveInfo(articleTitle, articleContent);
            notifyUpdateArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    @Override
    public void searchAllArticleCoincidencesInWikipedia(String textToSearch) {
        if(isValidString(textToSearch)) {
            allCoincidences = ResponseParser.searchAllCoincidencesInWikipedia(SearchLogic.executeSearchOfTermInWikipedia(textToSearch));
            notifyFoundArticles();
        } else
            notifyErrorOccurred("Empty Search Field");
    }

    @Override
    public List<SearchResult> getAllArticleCoincidencesInWikipedia() {
        return allCoincidences;
    }

    @Override
    public void searchFirstTermArticleInWikipedia(SearchResult searchResult) {
        articleInWikipedia = ResponseParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipediaForFirstTerm(searchResult));
        notifyFoundArticleContent();
    }

    @Override
    public void searchCompleteArticleInWikipedia(SearchResult searchResult){
        articleInWikipedia = ResponseParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipediaForEntireArticle(searchResult));
        notifyFoundArticleContent();
    }

    @Override
    public String getSearchedArticleInWikipedia() {
        return articleInWikipedia;
    }

    @Override
    public void selectStoredArticle(String articleTitle) {

        System.out.println(" --------------- stored");
        System.out.println(articleTitle);
        System.out.println(" --------------- stored");

        if(isValidString(articleTitle)) {
            articleContent = DataBase.getContent(articleTitle);
            notifyLoadArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    @Override
    public String getSelectedStoredArticleContent() {
        return articleContent;
    }

    @Override
    public Object[] getTitlesOfStoredArticles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
