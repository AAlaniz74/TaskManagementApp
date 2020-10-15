package com.sdproject.app.database;

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import java.util.ArrayList;

public class DummyDatabase implements Database {
  private ArrayList<User> allUsers;

  public DummyDatabase() {
    this.allUsers = new ArrayList<User>();
  }

  public void insert(Query q) {
    if (q.getTable().equals("User")) {
      insertUser(q);
    }
  }

  public void delete(Query q) {
    if (q.getTable().equals("User")) {
      deleteUser(q);
    }
  }

  public void modify(Query q) {
    if (q.getTable().equals("User")) {
      modifyUser(q);
    }
  }

  public <T> ArrayList<T> get(Query q) {
    if (q.getTable().equals("User")) {
      return (ArrayList<T>) getUsers(q);
    }
    return null;
  }

  //USER METHODS

  public void insertUser(Query q) {
    UserType type = UserType.valueOf(q.getUserType());
    User newUser = new User(q.getUserName(), q.getUserPass(), type);
    allUsers.add(newUser);
  }

  public void deleteUser(Query q) {
    ArrayList<User> selectedUser = getUsers(q);
    allUsers.remove(selectedUser.get(0));
  }

  public ArrayList<User> getUsers(Query q) {
    ArrayList<User> res = new ArrayList<User>();
    for (User user : allUsers) {
      if (verifyUserMatchesQuery(user, q))
        res.add(user);
    }
    return res;
  }

  public void modifyUser(Query q) {
    ArrayList<User> searchedUser = getUsers(q.getToModify());
    User modifiedUser = searchedUser.get(0);

    if (q.getUserName() != null)
	    allUsers.get(allUsers.indexOf(modifiedUser)).setUserName(q.getUserName());
    if (q.getUserPass() != null)
            allUsers.get(allUsers.indexOf(modifiedUser)).setUserPass(q.getUserPass());
    if (q.getUserType() != null)
            allUsers.get(allUsers.indexOf(modifiedUser)).setUserType(UserType.valueOf(q.getUserType()));
  }

  private boolean verifyUserMatchesQuery(User user, Query q) {
    boolean testName = (q.getUserName() == null) || ((q.getUserName() != null) && user.getUserName().equals(q.getUserName()));
    boolean testType = (q.getUserType() == null) || ((q.getUserType() != null) && user.getUserType().name().equals(q.getUserType()));
    return (testName && testType);
  }

  public boolean checkNoDuplicateUser(Query q) {
    Query dupeCheck = new Query().tableIs(q.getTable()).userNameIs(q.getUserName());
    ArrayList<User> searchedUser = getUsers(dupeCheck);
    return (searchedUser.size() == 0);
  }

  public boolean checkReturnsOneUser(Query q) {
    ArrayList<User> searchedUser = getUsers(q);
    return (searchedUser.size() == 1);
  }

  //AUXILIARY METHODS

  public ArrayList<User> getAllUsers() {
    return this.allUsers;
  }

}
