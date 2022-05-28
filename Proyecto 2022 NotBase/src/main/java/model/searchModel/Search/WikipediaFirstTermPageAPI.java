package model.searchModel.Search;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaFirstTermPageAPI {

  //Attention! this api interface gets only the first extract of the wikipage (the summary section at the beginning)
  //If we want to fetch all the text in the wikipage we can copy-paste this class and remove "&exintro=1" from the GET
  @GET("api.php?format=json&action=query&prop=extracts&exintro=1")
  Call<String> getExtractByPageID(@Query("pageids") String term);

}
