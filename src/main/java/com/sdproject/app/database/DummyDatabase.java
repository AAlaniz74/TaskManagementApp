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

  public <T> ArrayList<T> get(Query q) {
    if (q.getTable().equals("User")) {
      return (ArrayList<T>) getUsers(q);
    }
    return null;
  }

  public void insertUser(Query q) {
    UserType type = UserType.valueOf(q.getUserType());
    User newUser = new User(q.getUserName(), q.getUserPass(), type);
    allUsers.add(newUser);
  }

  public ArrayList<User> getUsers(Query q) {
    ArrayList<User> res = new ArrayList<User>();
    for (User user : allUsers) {
      if (getUserCheck(user, q))
        res.add(user);
    }
    return res;
  }

  private boolean getUserCheck(User user, Query q) {
    boolean testName = (q.getUserName() == null) || ((q.getUserName() != null) && user.getUserName().equals(q.getUserName()));
    boolean testType = (q.getUserType() == null) || ((q.getUserType() != null) && user.getUserType().name().equals(q.getUserType()));
    return (testName && testType);
  }

  public ArrayList<User> getAllUsers() {
    return this.allUsers;
  }

}
