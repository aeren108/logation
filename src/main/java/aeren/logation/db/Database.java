package aeren.logation.db;


import aeren.logation.LogationMain;
import aeren.logation.models.User;
import java.sql.*;

public class Database {
  private static Database instance = null;

  private Connection con;
  private boolean initialized = false;

  public static final String LOGS_TABLE = "logs";
  public static final String NAME_COL = "name";
  public static final String LOCATIONS_COL = "locations";
  public static final String DEATHS_COL = "deaths";

  private Database() {
    initDb();
  }

  public static Database getInstance() {
    if (instance == null)
      instance = new Database();

    return instance;
  }

  private void initDb() {
    try {
      con = this.connect("logation.db");

      Statement s = con.createStatement();
      String tableSql = "CREATE TABLE IF NOT EXISTS " + LOGS_TABLE + "(name TEXT NOT NULL UNIQUE," +
        "locations TEXT NOT NULL," +
        "deaths TEXT NOT NULL)";

      s.executeUpdate(tableSql);
      s.close();
    } catch (SQLException e) {
      return;
    }

    initialized = true;
  }

  private Connection connect(String url) throws SQLException {
    Connection con = null;
    con = DriverManager.getConnection("jdbc:sqlite:" + url);

    return con;
  }

  public User getUserByName(String username) {
    if (!initialized)
      return null;

    try {
      String query = "SELECT * FROM logs WHERE name = ?";
      PreparedStatement ps = con.prepareStatement(query);

      ps.setString(1, username);

      ResultSet result = ps.executeQuery();

      if (result.next()) {
        String name = result.getString(NAME_COL);
        String locations = result.getString(LOCATIONS_COL);
        String deaths = result.getString(DEATHS_COL);

        return new User(name, locations, deaths);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public boolean createUser(User user) {
    if (!initialized)
      return false;

    try {
      String sql = "INSERT INTO logs(name, locations, deaths) VALUES(?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sql);

      ps.setString(1, user.getName());
      ps.setString(2, user.getLocations());
      ps.setString(3, user.getDeaths());

      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean updateUser(User user) {
    if (!initialized)
      return false;

    try {
      String sql = "UPDATE logs SET locations = ?, deaths = ? WHERE name = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      ps.setString(1, user.getLocations());
      ps.setString(2, user.getDeaths());
      ps.setString(3, user.getName());

      ps.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  public boolean deleteUser(User user) {
    if (!initialized)
      return false;

    try {
      String sql = "DELETE FROM logs WHERE name = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      ps.setString(1, user.getName());

      ps.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  public void close() {
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
