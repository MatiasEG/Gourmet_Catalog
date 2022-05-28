package model.searchModel;

import model.searchModel.Search.SearchLogic;
import model.searchModel.Search.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements SearchModelInterface {

    private List<SearchResult> allCoincidencesFound;
    private String foundArticleContent;
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

    private void notifyFoundCoincidences(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticleCoincidences();
    }

    private void notifyFoundArticleContent(){
        for(SearchListener searchListener : searchListeners)
            searchListener.didFindArticleContent();
    }

    private void notifyErrorOccurred(String errorMessage){
        for(ErrorListener errorListener : errorListeners)
            errorListener.didErrorOccurred(errorMessage);
    }

    private boolean isEmptyOrInvalid(String string){
        return string == null || string.equals("");
    }

    @Override
    public void searchAllCoincidencesInWikipedia(String textToSearch) {
        if(isEmptyOrInvalid(textToSearch))
            notifyErrorOccurred("Empty Search Field");
        else {
            Response<String> allCoincidencesResponse = SearchLogic.executeSearchOfTermInWikipedia(textToSearch);
            allCoincidencesFound = ResponseParser.parseWikipediaCoincidences(allCoincidencesResponse);
            notifyFoundCoincidences();
        }
    }

    @Override
    public List<SearchResult> getAllCoincidencesFound() {
        return allCoincidencesFound;
    }

    @Override
    public void searchArticleSummaryInWikipedia(SearchResult searchResult) {
        Response<String> foundArticleContentResponse = SearchLogic.executeSpecificSearchInWikipediaForFirstTerm(searchResult);
        foundArticleContent = ResponseParser.parseWikipediaArticle(foundArticleContentResponse);
        notifyFoundArticleContent();
    }

    @Override
    public void searchFullArticleInWikipedia(SearchResult searchResult){
        Response<String> foundArticleContentResponse = SearchLogic.executeSpecificSearchInWikipediaForEntireArticle(searchResult);
        foundArticleContent = ResponseParser.parseWikipediaArticle(foundArticleContentResponse);
        notifyFoundArticleContent();
    }

    @Override
    public String getFoundArticleContent() {
        return foundArticleContent;
    }
}
