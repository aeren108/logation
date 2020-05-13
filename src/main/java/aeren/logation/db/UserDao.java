package aeren.logation.db;

import aeren.logation.models.User;

public interface UserDao {
  void createUserTable();
  User getUserByName(String name);
  boolean createUser(User user);
  boolean updateUser(User user);
  boolean deleteUser(User user);
}
