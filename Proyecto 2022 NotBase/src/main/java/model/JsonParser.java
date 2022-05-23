package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Search.SearchResult;
import retrofit2.Response;

import java.util.*;

public class JsonParser{

    public static List<SearchResult> searchAllCoincidencesInWikipedia(Response<String> wikipediaResponse) {
        JsonArray pagesJsonArray = getPagesAsJsonArray(wikipediaResponse);
        return translatePagesJsonArrayToSearchResultList(pagesJsonArray);
    }

    private static JsonArray getPagesAsJsonArray(Response<String> wikipediaResponse) {
        Gson gson = new Gson();
        JsonObject responseBody = gson.fromJson(wikipediaResponse.body(), JsonObject.class);
        JsonObject responseQuery = responseBody.get("query").getAsJsonObject();
        return responseQuery.get("search").getAsJsonArray();
    }

    private static List<SearchResult> translatePagesJsonArrayToSearchResultList(JsonArray pagesJsonArray){
        List<SearchResult> searchResults = new ArrayList<SearchResult>();
        for (JsonElement pageJsonElement : pagesJsonArray) {
            JsonObject pageJsonObject = pageJsonElement.getAsJsonObject();
            String searchResultTitle = pageJsonObject.get("title").getAsString();
            String searchResultPageId = pageJsonObject.get("pageid").getAsString();
            String searchResultSnippet = pageJsonObject.get("snippet").getAsString();
            SearchResult searchResult = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchResults.add(searchResult);
        }
        return searchResults;
    }

    public static String parseWikipediaResponse(Response<String> wikipediaResponse) {
        String articleContentString;
        JsonObject pageJsonObject = getPageAsJsonObject(wikipediaResponse);
        JsonElement articleContentJsonElement = pageJsonObject.get("extract");
        if (articleContentJsonElement == null) {
            articleContentString = "No Results";
        } else {
            articleContentString = "<h1>" + pageJsonObject.get("title") + "</h1>";
            articleContentString += articleContentJsonElement.getAsString().replace("\\n", "\n");
        }
        return articleContentString;
    }

    private static JsonObject getPageAsJsonObject(Response<String> wikipediaResponse) {
        Gson gson = new Gson();
        JsonObject responseBody = gson.fromJson(wikipediaResponse.body(), JsonObject.class);
        JsonObject responseQuery = responseBody.get("query").getAsJsonObject();
        JsonObject pages = responseQuery.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> firstPage = pagesSet.iterator().next();
        JsonObject page = firstPage.getValue().getAsJsonObject();
        return page;
    }
}
