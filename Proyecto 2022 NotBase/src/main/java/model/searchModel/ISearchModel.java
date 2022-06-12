package model.searchModel;

import model.searchModel.searchLogic.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;

import java.util.List;

public interface ISearchModel {

    void addSearchListener(SearchListener searchListener);
    void addErrorListener(ErrorListener errorListener);

    void searchAllCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllCoincidencesFound();

    void searchArticleSummaryInWikipedia(SearchResult searchResult);
    void searchFullArticleInWikipedia(SearchResult searchResult);

    String getFoundArticleContent();
}
