package model;

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

    @Override
    public void deleteArticle(String articleTitle) {
        DataBase.deleteEntry(articleTitle);
        notifyDeleteArticle();
    }

    @Override
    public void saveArticle(String articleTitle, String articleExtract) {
        DataBase.saveInfo(articleTitle, articleExtract);
        notifySaveArticle();
    }

    @Override
    public void updateArticle(String articleTitle, String articleExtract) {
        DataBase.saveInfo(articleTitle, articleExtract);
        notifyUpdateArticle();
    }

    @Override
    public void searchAllArticleCoincidencesInWikipedia(String textToSearch) {
        allCoincidences = JsonParser.searchAllCoincidencesInWikipedia(SearchLogic.executeSearchOfTermInWikipedia(textToSearch));
        notifyFoundArticles();
    }

    @Override
    public List<SearchResult> getAllArticleCoincidencesInWikipedia() {
        return allCoincidences;
    }

    @Override
    public void searchArticleInWikipedia(SearchResult searchResult) {
        articleInWikipedia = JsonParser.parseWikipediaResponse(SearchLogic.executeSpecificSearchInWikipedia(searchResult));
        notifyFoundExtract();
    }

    @Override
    public String getSearchedArticleInWikipedia() {
        return articleInWikipedia;
    }

    @Override
    public void selectStoredArticleExtract(String articleTitle) {
        articleExtract = DataBase.getExtract(articleTitle);
        notifyLoadArticle();
    }

    @Override
    public String getExtractOfSelectedStoredArticle() {
        return articleExtract;
    }

    @Override
    public Object[] getTitlesOfStoredArticles(){ return DataBase.getTitles().stream().sorted().toArray(); }
}
