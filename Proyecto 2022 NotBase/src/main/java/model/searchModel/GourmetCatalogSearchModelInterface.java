package model.searchModel;

import model.Search.SearchResult;
import model.listeners.ErrorListener;
import model.listeners.SearchListener;

import java.util.List;

public interface GourmetCatalogSearchModelInterface {

    void addSearchListener(SearchListener searchListener);
    //TODO este error listener no se usa, pero creo que va por si hay algun error de la busqueda?
    void addErrorListener(ErrorListener errorListener);
    void searchAllArticleCoincidencesInWikipedia(String textToSearch);
    List<SearchResult> getAllArticleCoincidencesInWikipedia();

    void searchFirstTermArticleInWikipedia(SearchResult searchResult);
    void searchCompleteArticleInWikipedia(SearchResult searchResult);
    String getSearchedArticleInWikipedia();
}
