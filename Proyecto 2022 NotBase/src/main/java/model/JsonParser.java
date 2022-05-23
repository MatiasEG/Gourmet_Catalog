package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;

import java.util.*;

public class JsonParser{

    public static List<SearchResult> searchAllCoincidencesInWikipedia(JsonArray jsonArray) {
        return translateWebTextInJsonFormat2SearchResult(jsonArray);
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

    public static String parseWikipediaResponse(Response<String> wikipediaResponse) {

        String extractOfArticle = "";

        Gson gson = new Gson();

        System.out.println("JSON " + wikipediaResponse.body());
        JsonObject jobj2 = gson.fromJson(wikipediaResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");


        if (searchResultExtract2 == null) {
            extractOfArticle = "No Results";
        } else {
            extractOfArticle = "<h1>" + page.get("title") + "</h1>";
            extractOfArticle += searchResultExtract2.getAsString().replace("\\n", "\n");
            return extractOfArticle;
        }

        return extractOfArticle;
    }
}
