package model;

import com.google.gson.JsonArray;

public interface JsonParserInterface {

    JsonArray searchAllCoincidencesInWikipedia(String textToSearch);

    String searchArticleInWikipedia(String pageID);

}
