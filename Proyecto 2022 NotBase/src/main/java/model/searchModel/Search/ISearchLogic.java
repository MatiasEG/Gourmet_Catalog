package model.searchModel.Search;

import retrofit2.Response;

public interface ISearchLogic {

    Response<String> executeSearchOfTermInWikipedia(String textToSearch) throws Exception;
    Response<String> executeSpecificSearchInWikipediaForFirstTerm(SearchResult searchResult) throws Exception;
    Response<String> executeSpecificSearchInWikipediaForEntireArticle(SearchResult searchResult) throws Exception;
}
