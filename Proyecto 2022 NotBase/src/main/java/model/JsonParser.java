package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.*;

public class JsonParser{

    public static List<SearchResult> searchAllCoincidencesInWikipedia(String textToSearch) {
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


        return translateWebTextInJsonFormat2SearchResult(jsonArrayToReturn);
    }

    private static List<SearchResult> translateWebTextInJsonFormat2SearchResult(JsonArray jsonArray){
        List<SearchResult> resultsToReturn = new ArrayList<SearchResult>();

        for (JsonElement je : jsonArray) {
            JsonObject searchResult = je.getAsJsonObject();
            String searchResultTitle = searchResult.get("title").getAsString();
            String searchResultPageId = searchResult.get("pageid").getAsString();
            String searchResultSnippet = searchResult.get("snippet").getAsString();

            SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            resultsToReturn.add(sr);
        }
        return resultsToReturn;
    }

    public static String searchArticleInWikipedia(SearchResult searchResult) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

        String extractOfArticle = "";

        try{
            Response<String> callForPageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();

            Gson gson = new Gson();

            System.out.println("JSON " + callForPageResponse.body());
            JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query2 = jobj2.get("query").getAsJsonObject();
            JsonObject pages = query2.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract2 = page.get("extract");


            if (searchResultExtract2 == null) {
                extractOfArticle = "No Results";
            } else {
                extractOfArticle = "<h1>" + searchResult.getTitle() + "</h1>";
                extractOfArticle += searchResultExtract2.getAsString().replace("\\n", "\n");
                return extractOfArticle;
            }
        } catch (Exception e12) {
            System.out.println(e12.getMessage());
        }
        return extractOfArticle;
    }
}
