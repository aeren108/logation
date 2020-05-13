package aeren.logation.db;

import aeren.logation.models.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {

  public static final String NAME_COL = "name";
  public static final String LOCATIONS_COL = "locations";
  public static final String DEATHS_COL = "deaths";

  @Override
  public void createUserTable() {
    Connection conn = ConnectionFactory.getConnection();
    try {
      Statement s = conn.createStatement();
      String tableSql = "CREATE TABLE IF NOT EXISTS logs (name TEXT NOT NULL UNIQUE," +
                                                          "locations TEXT NOT NULL," +
                                                          "deaths TEXT NOT NULL)";

      s.executeUpdate(tableSql);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn);
    }
  }

  @Override
  public User getUserByName(String username) {
    Connection conn = ConnectionFactory.getConnection();

    try {
      String query = "SELECT * FROM logs WHERE name = ?";
      PreparedStatement ps = conn.prepareStatement(query);

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
    } finally {
      close(conn);
    }

    return null;
  }

  @Override
  public boolean createUser(User user) {
    Connection conn = ConnectionFactory.getConnection();

    try {
      String sql = "INSERT INTO logs(name, locations, deaths) VALUES(?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, user.getName());
      ps.setString(2, user.getLocations());
      ps.setString(3, user.getDeaths());

      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn);
    }

    return false;
  }

  @Override
  public boolean updateUser(User user) {
    Connection conn = ConnectionFactory.getConnection();

    try {
      String sql = "UPDATE logs SET locations = ?, deaths = ? WHERE name = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, user.getLocations());
      ps.setString(2, user.getDeaths());
      ps.setString(3, user.getName());

      ps.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn);
    }

    return false;
  }

  @Override
  public boolean deleteUser(User user) {
    Connection conn = ConnectionFactory.getConnection();

    try {
      String sql = "DELETE FROM logs WHERE name = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, user.getName());

      ps.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(conn);
    }

    return false;
  }

  private void close(Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
