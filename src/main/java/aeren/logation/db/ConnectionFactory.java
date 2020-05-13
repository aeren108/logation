package aeren.logation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

  public static final String URL = "jdbc:sqlite:logation.db";

  public static Connection getConnection() {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(URL);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return conn;
  }

}
