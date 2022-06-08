package model.searchModel.searchLogic;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

public class SearchLogic implements ISearchLogic{

    private Retrofit retrofit;

    public SearchLogic(){ setRetrofit(); }

    private void setRetrofit(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public List<SearchResult> searchTermInWikipedia(String textToSearch) throws Exception {
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        Response<String> searchResponse = null;
        try{
            searchResponse = searchAPI.searchForTerm(textToSearch + " articletopic:\"food-and-drink\"").execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia all coincidences for '"+textToSearch+"'");
        }
        return ResponseParser.parseWikipediaCoincidences(searchResponse);
    }

    public String searchArticleSummaryInWikipedia(SearchResult searchResult) throws Exception {
        WikipediaFirstTermPageAPI pageAPI = retrofit.create(WikipediaFirstTermPageAPI.class);
        Response<String> pageResponse = null;
        try{
            pageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia the first extract of the article");
        }
        return ResponseParser.parseWikipediaArticle(pageResponse);
    }

    public String searchFullArticleInWikipedia(SearchResult searchResult) throws Exception {
        WikipediaAllArticlePageAPI pageAPI = retrofit.create(WikipediaAllArticlePageAPI.class);
        Response<String> pageResponse = null;
        try{
            pageResponse = pageAPI.getExtractByPageID(searchResult.getPageID()).execute();
        } catch (Exception e) {
            throw new Exception("Error searching in Wikipedia the full article");
        }
        return ResponseParser.parseWikipediaArticle(pageResponse);
    }
}
