package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.gourmetCatalog.fulllogic.WikipediaPageAPI;
import dyds.gourmetCatalog.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.Map;
import java.util.Set;

public class JsonParser{

    public static JsonArray searchAllCoincidencesInWikipedia(String textToSearch) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        Response<String > callForSearchResponse = searchAPI.searchForTerm(textToSearch + " articletopic:\"food-and-drink\"").execute();

        System.out.println("JSON " + callForSearchResponse.body());

        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        return query.get("search").getAsJsonArray();
    }

    public static String searchArticleInWikipedia(String pageID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

        Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

        Gson gson = new Gson();

        System.out.println("JSON " + callForPageResponse.body());
        JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");

        String extractOfArticle;
        if (searchResultExtract2 == null) {
            extractOfArticle = "No Results";
        } else {
            // TODO how can I get the title? Ask fot it to the presenter?
//            extractOfArticle = "<h1>" + sr.title + "</h1>";
//            selectedResultTitle = sr.title;
            // En esta linea deberia ir un +=
            extractOfArticle = searchResultExtract2.getAsString().replace("\\n", "\n");
            return extractOfArticle;
        }

    }
}
