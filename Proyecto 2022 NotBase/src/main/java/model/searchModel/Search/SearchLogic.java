package model.searchModel.Search;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchLogic {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    public static Response<String> executeSearchOfTermInWikipedia(String textToSearch) throws Exception {
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        Response<String> callForSearchResponse = null;
        try{
            callForSearchResponse = searchAPI.searchForTerm(textToSearch + " articletopic:\"food-and-drink\"").execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia all coincidences for '"+textToSearch+"'");
        }
        return callForSearchResponse;
    }

    public static Response<String> executeSpecificSearchInWikipediaForFirstTerm(SearchResult searchResult) throws Exception {
        WikipediaFirstTermPageAPI pageAPI = retrofit.create(WikipediaFirstTermPageAPI.class);
        Response<String> callForPageResponse = null;
        try{
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia the first extract of the article");
        }
        return callForPageResponse;
    }

    public static Response<String> executeSpecificSearchInWikipediaForEntireArticle(SearchResult searchResult) throws Exception {
        WikipediaAllArticlePageAPI pageAPI = retrofit.create(WikipediaAllArticlePageAPI.class);
        Response<String> callForPageResponse = null;
        try{
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia the full article");
        }
        return callForPageResponse;
    }
}
