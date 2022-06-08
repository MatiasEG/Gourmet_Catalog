package model.searchModel.searchLogic;

import java.util.List;

public interface ISearchLogic {

    List<SearchResult> searchTermInWikipedia(String textToSearch) throws Exception;
    String searchArticleSummaryInWikipedia(SearchResult searchResult) throws Exception;
    String searchFullArticleInWikipedia(SearchResult searchResult) throws Exception;
}
