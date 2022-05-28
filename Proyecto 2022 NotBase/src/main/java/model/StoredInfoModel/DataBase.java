package model.StoredInfoModel;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
  private final static String databaseUrl = "jdbc:sqlite:./dictionary.db";

  public static void loadDatabase() {
    //If the database doesnt exists we create it

    try (Connection connection = DriverManager.getConnection(databaseUrl)) {
      if (connection != null) {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      //TODO If the DB was created before, a SQL error is reported but it is not harmfull...
    }
  }

  public static void testDB()
  {
    //TODO este método no se usa

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(databaseUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next())
      {
        // read the result set
        System.out.println("id = " + rs.getInt("id"));
        System.out.println("title = " + rs.getString("title"));
        System.out.println("content = " + rs.getString("extract"));
        System.out.println("source = " + rs.getString("source"));

      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }

  public static ArrayList<String> getTitles()
  {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(databaseUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next()) titles.add(rs.getString("title"));
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
      return titles;
    }
  }

  public static void saveInfo(String title, String content)
  {
    title = title.replace("'", "''");
    content = content.replace("'", "''");
    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(databaseUrl);

      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("replace into catalog values(null, '"+ title + "', '"+ content + "', 1)");
    }
    catch(SQLException e)
    {
      System.err.println("Error saving " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println( e);
      }
    }
  }

  public static String getContent(String title)
  {

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(databaseUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
      rs.next();
      return rs.getString("extract");
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println("Get title error " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
    return null;
  }

  public static void deleteEntry(String title)
  {

    Connection connection = null;
    try
    {
      connection = DriverManager.getConnection(databaseUrl);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );

    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println("Get title error " + e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }


}
