package model.Search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchLogic {

    public static JsonArray executeSearchOfTermInWikipedia(String textToSearch){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);

        JsonArray jsonArrayToReturn = null;

        try{
            Response<String > callForSearchResponse = searchAPI.searchForTerm(textToSearch + " articletopic:\"food-and-drink\"").execute();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get("query").getAsJsonObject();
            jsonArrayToReturn = query.get("search").getAsJsonArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jsonArrayToReturn;
    }

    public static Response<String> executeSpecificSearchInWikipediaForFirstTerm(SearchResult searchResult){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

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
