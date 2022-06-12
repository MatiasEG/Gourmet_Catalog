package model.searchModel.searchLogic;

import java.util.List;

public interface ISearchLogic {

    List<SearchResult> searchTermInWikipediaAndParse(String textToSearch) throws Exception;
    String searchArticleSummaryInWikipediaAndParse(SearchResult searchResult) throws Exception;
    String searchFullArticleInWikipediaAndParse(SearchResult searchResult) throws Exception;
}
