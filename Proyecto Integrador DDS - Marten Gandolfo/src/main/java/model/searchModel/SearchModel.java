package model.searchModel;

import model.searchModel.searchLogic.ISearchLogic;
import model.searchModel.searchLogic.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements ISearchModel {

    private List<SearchResult> allCoincidencesFound;
    private String foundArticleContent;
    private List<SearchListener> searchListeners = new ArrayList<>();
    private List<ErrorListener> errorListeners = new ArrayList<>();
    private ISearchLogic searchLogic;

    public SearchModel(ISearchLogic searchLogic){
        this.searchLogic = searchLogic;
    }

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
            allCoincidencesFound = null;
            try {
                allCoincidencesFound = searchLogic.searchTermInWikipediaAndParse(textToSearch);
                notifyFoundCoincidences();
            }catch(Exception e){
                notifyErrorOccurred("Search error");
            }
        }
    }

    @Override
    public List<SearchResult> getAllCoincidencesFound() {
        return allCoincidencesFound;
    }

    @Override
    public void searchArticleSummaryInWikipedia(SearchResult searchResult) {
        foundArticleContent = null;
        try {
            foundArticleContent = searchLogic.searchArticleSummaryInWikipediaAndParse(searchResult);
            foundArticleContent = removeHtmlLinks(foundArticleContent);
            notifyFoundArticleContent();
        }catch(Exception e){
            notifyErrorOccurred("Search error");
        }
    }

    @Override
    public void searchFullArticleInWikipedia(SearchResult searchResult){
        foundArticleContent = null;
        try {
            foundArticleContent = searchLogic.searchFullArticleInWikipediaAndParse(searchResult);
            foundArticleContent = removeHtmlLinks(foundArticleContent);
            notifyFoundArticleContent();
        }catch(Exception e){
            notifyErrorOccurred("Search error");
        }
    }

    private String removeHtmlLinks(String string){
        return string.replaceAll("\\<link.*?>", "");
    }

    @Override
    public String getFoundArticleContent() {
        return foundArticleContent;
    }
}
