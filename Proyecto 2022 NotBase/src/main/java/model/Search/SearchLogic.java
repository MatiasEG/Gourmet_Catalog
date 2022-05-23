package model.Search;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchLogic {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    public static Response<String> executeSearchOfTermInWikipedia(String textToSearch){
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        Response<String> callForSearchResponse = null;
        try{
            callForSearchResponse = searchAPI.searchForTerm(textToSearch + " articletopic:\"food-and-drink\"").execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return callForSearchResponse;
    }

    public static Response<String> executeSpecificSearchInWikipediaForFirstTerm(SearchResult searchResult){
        WikipediaFirstTermPageAPI pageAPI = retrofit.create(WikipediaFirstTermPageAPI.class);
        Response<String> callForPageResponse = null;
        try{
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return callForPageResponse;
    }

    public static Response<String> executeSpecificSearchInWikipediaForEntireArticle(SearchResult searchResult){
        WikipediaAllArticlePageAPI pageAPI = retrofit.create(WikipediaAllArticlePageAPI.class);
        Response<String> callForPageResponse = null;
        try{
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return callForPageResponse;
    }
}
