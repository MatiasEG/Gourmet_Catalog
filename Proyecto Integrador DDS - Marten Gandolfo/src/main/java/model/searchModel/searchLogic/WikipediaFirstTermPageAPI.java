package model.searchModel.searchLogic;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaFirstTermPageAPI {

  @GET("api.php?format=json&action=query&prop=extracts&exintro=1")
  Call<String> getExtractByPageID(@Query("pageids") String term);

}
