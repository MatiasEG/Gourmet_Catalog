package model.storedInfoModel;

import java.util.ArrayList;

public interface IDataBase {
    void createDatabaseIfDoesNotExists();
    ArrayList<String> getAllArticleTitles() throws Exception;
    void saveArticle(String title, String content) throws Exception;
    String getArticleContent(String title) throws Exception;
    void deleteArticle(String title) throws Exception;
    void clearDataBase() throws Exception;
}
