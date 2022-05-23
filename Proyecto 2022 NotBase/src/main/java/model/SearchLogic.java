package model;

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

            System.out.println("JSON " + callForSearchResponse.body());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get("query").getAsJsonObject();
            jsonArrayToReturn = query.get("search").getAsJsonArray();
        } catch (Exception e12) {
            System.out.println(e12.getMessage());
        }

        return jsonArrayToReturn;
    }

    public static Response<String> executeSpecificSearchInWikipedia(SearchResult searchResult){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

        Response<String> callForPageResponse = null;

        try{
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e12) {
            System.out.println(e12.getMessage());
        }
        return callForPageResponse;
    }
}
