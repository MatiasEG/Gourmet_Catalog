package model.searchModel;

import model.searchModel.Search.ISearchLogic;
import model.searchModel.Search.SearchLogic;
import model.searchModel.Search.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements ISearchModel {

    private List<SearchResult> allCoincidencesFound;
    private String foundArticleContent;
    private List<SearchListener> searchListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();
    private ISearchLogic searchLogic = new SearchLogic();

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
            Response<String> allCoincidencesResponse = null;
            try {
                allCoincidencesResponse = searchLogic.executeSearchOfTermInWikipedia(textToSearch);
            }catch(Exception e){
                notifyErrorOccurred("Search error");
            }
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
        Response<String> foundArticleContentResponse = null;
        try {
            foundArticleContentResponse = searchLogic.executeSpecificSearchInWikipediaForFirstTerm(searchResult);
        }catch(Exception e){
            notifyErrorOccurred("Search error");
        }
        foundArticleContent = ResponseParser.parseWikipediaArticle(foundArticleContentResponse);
        notifyFoundArticleContent();
    }

    @Override
    public void searchFullArticleInWikipedia(SearchResult searchResult){
        Response<String> foundArticleContentResponse = null;
        try {
            foundArticleContentResponse = searchLogic.executeSpecificSearchInWikipediaForEntireArticle(searchResult);
        }catch(Exception e){
            notifyErrorOccurred("Search error");
        }
        foundArticleContent = ResponseParser.parseWikipediaArticle(foundArticleContentResponse);
        notifyFoundArticleContent();
    }

    @Override
    public String getFoundArticleContent() {
        return foundArticleContent;
    }
}
