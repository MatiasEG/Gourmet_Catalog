package model.searchModel;

import model.searchModel.Search.SearchLogic;
import model.searchModel.Search.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements SearchModelInterface {

    private List<SearchResult> allCoincidences;
    private String articleInWikipedia;
    private List<SearchListener> searchListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();

    @Override
    public void addSearchListener(SearchListener searchListener) {
        searchListeners.add(searchListener);
    }

    @Override
    public void addErrorListener(ErrorListener errorListener){
        errorListeners.add(errorListener);
    }

    private void notifyFoundArticles(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticles();
    }

    private void notifyFoundArticleContent(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticleContent();
    }

    private void notifyErrorOccurred(String errorMessage){
        for(ErrorListener errorListener : errorListeners)
            errorListener.didErrorOccurred(errorMessage);
    }

    private boolean isValidString(String string){
        return string != null && !string.equals("");
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
}
