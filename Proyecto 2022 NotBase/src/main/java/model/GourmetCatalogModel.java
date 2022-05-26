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

    private String articleExtract;
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

    private void notifyFoundExtract(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindExtract();
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
    public void saveArticle(String articleTitle, String articleExtract) {
        DataBase.saveInfo(articleTitle, articleExtract);
        notifySaveArticle();
    }

    @Override
    public void updateArticle(String articleTitle, String articleExtract) {
        if(isValidString(articleTitle)) {
            DataBase.saveInfo(articleTitle, articleExtract);
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
        notifyFoundExtract();
    }

    @Override
    public void searchCompleteArticleInWikipedia(SearchResult searchResult){
        articleInWikipedia = ResponseParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipediaForEntireArticle(searchResult));
        notifyFoundExtract();
    }

    @Override
    public String getSearchedArticleInWikipedia() {
        return articleInWikipedia;
    }

    @Override
    public void selectStoredArticleExtract(String articleTitle) {
        if(isValidString(articleTitle)) {
            articleExtract = DataBase.getExtract(articleTitle);
            notifyLoadArticle();
        } else
            notifyErrorOccurred("Article Not Selected");
    }

    @Override
    public String getExtractOfSelectedStoredArticle() {
        return articleExtract;
    }

    @Override
    public Object[] getTitlesOfStoredArticles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
