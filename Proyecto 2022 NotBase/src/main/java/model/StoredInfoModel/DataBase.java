package model.StoredInfoModel;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
  private final static String databaseUrl = "jdbc:sqlite:./dictionary.db";

  public static void createDatabaseIfDoesNotExists() {
    try (Connection connection = DriverManager.getConnection(databaseUrl)) {
      executeUpdate(connection, "create table articles (id INTEGER, title string PRIMARY KEY, content string, source integer)");
    } catch (SQLException e) {
      //TODO database already exists
    }
  }

  private static void executeUpdate(Connection connection, String sql) throws SQLException {
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);
    statement.executeUpdate(sql);
  }

  private static ResultSet executeQuery(Connection connection, String sql) throws SQLException{
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);
    return statement.executeQuery(sql);
  }

  public static ArrayList<String> getAllArticleTitles() throws Exception{
    ArrayList<String> titles = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(databaseUrl)){
      ResultSet resultSet = executeQuery(connection, "select * from articles");
      while(resultSet.next()) titles.add(resultSet.getString("title"));
    }
    catch(SQLException e) {
      throw new Exception("Error while getting article titles from the database");
    }
    return titles;
  }

  public static void saveArticle(String title, String content) throws Exception{
    title = escapeSpecialCharacters(title);
    content = escapeSpecialCharacters(content);
    try (Connection connection = DriverManager.getConnection(databaseUrl)){
      executeUpdate(connection, "replace into articles values(null, '"+ title + "', '"+ content + "', 1)");
    }
    catch(SQLException e){
      throw new Exception("Error while saving article in the database");
    }
  }

  private static String escapeSpecialCharacters(String title) {
    return title.replace("'", "''");
  }

  public static String getArticleContent(String title) throws Exception{
    try (Connection connection = DriverManager.getConnection(databaseUrl)){
      ResultSet resultSet = executeQuery(connection, "select * from articles WHERE title = '" + title + "'" );
      resultSet.next();
      return resultSet.getString("content");
    }
    catch(SQLException e) {
      throw new Exception("Error while getting article content from the database");
    }
  }

  public static void deleteArticle(String title) throws Exception{
    try (Connection connection = DriverManager.getConnection(databaseUrl)){
      executeUpdate(connection, "DELETE FROM articles WHERE title = '" + title + "'" );
    }
    catch(SQLException e) {
      throw new Exception("Error while deleting article from the database");
    }
  }
}
