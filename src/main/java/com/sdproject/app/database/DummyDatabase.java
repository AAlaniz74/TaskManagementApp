package com.sdproject.app.database;

import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;
import java.util.ArrayList;

public class DummyDatabase implements Database {
  private ArrayList<User> allUsers;
  private ArrayList<Task> allTasks;

  public DummyDatabase() {
    this.allUsers = new ArrayList<User>();
    this.allTasks = new ArrayList<Task>();
  }

  public int insert(Query q) {
    if (q.getTable().equals("User")) {
      return insertUser(q);
    } else if (q.getTable().equals("Task")) {
      return insertTask(q);
    } else {
      return -1;
    }
  }

  public int delete(Query q) {
    if (q.getTable().equals("User")) {
      return deleteUser(q);
    } else if (q.getTable().equals("Task")) {
      return deleteTask(q);
    } else {
      return -1;
    }
  }

  public int modify(Query q) {
    if (q.getTable().equals("User")) {
      return modifyUser(q);
    } else if (q.getTable().equals("Task")) {
      return modifyTask(q);
    } else {
      return -1;
    }
  }

  public <T> ArrayList<T> get(Query q) {
    if (q.getTable().equals("User")) {
      return (ArrayList<T>) getUsers(q);
    } else if (q.getTable().equals("Tasks")) {
      return (ArrayList<T>) getTasks(q);
    }
    return null;
  }

  public <T> T getOne(Query q) {
    return (T) get(q).get(0);
  }

  //USER METHODS

  public int insertUser(Query q) {
    UserType type = UserType.valueOf(q.getUserType());
    User newUser = new User(q.getUserName(), q.getUserPass(), type);
    allUsers.add(newUser);
    return newUser.getUserId();
  }

  public int deleteUser(Query q) {
    User deletedUser = getOne(q);
    int retval = deletedUser.getUserId();
    allUsers.remove(deletedUser);
    return retval;
  }

  public ArrayList<User> getUsers(Query q) {
    ArrayList<User> res = new ArrayList<User>();
    for (User user : allUsers) {
      if (verifyUserMatchesQuery(user, q))
        res.add(user);
    }
    return res;
  }

  public int modifyUser(Query q) {
    User modifiedUser = getOne(q);
	  
    if (q.getUserName() != null)
	    allUsers.get(allUsers.indexOf(modifiedUser)).setUserName(q.getUserName());
    if (q.getUserPass() != null)
            allUsers.get(allUsers.indexOf(modifiedUser)).setUserPass(q.getUserPass());
    if (q.getUserType() != null)
            allUsers.get(allUsers.indexOf(modifiedUser)).setUserType(UserType.valueOf(q.getUserType()));
    return modifiedUser.getUserId();
  }

  private boolean verifyUserMatchesQuery(User user, Query q) {
    boolean testName = (q.getUserName() == null) || ((q.getUserName() != null) && user.getUserName().equals(q.getUserName()));
    boolean testPass = (q.getUserPass() == null) || ((q.getUserPass() != null) && user.getUserPass().equals(q.getUserPass()));
    boolean testType = (q.getUserType() == null) || ((q.getUserType() != null) && user.getUserType().name().equals(q.getUserType()));
    boolean testID = (q.getUserId() == 0) || ((q.getUserId() != 0) && (user.getUserId() == q.getUserId()));
    return (testName && testPass && testType && testID);
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

  //TASK METHODS

  public int insertTask(Query q) {
    Task newTask = new Task(q.getTaskName(), q.getTaskDesc(), q.getCreatedById());
    optionalTaskFields(q, newTask);
    allTasks.add(newTask);
    return newTask.getTaskId();
  }

  private void optionalTaskFields(Query q, Task t) {
    if (q.getTaskStatus() != null)
	    t.setTaskStatus(TaskStatus.valueOf(q.getTaskStatus()));
    if (q.getAssignedToId() != 0)
	    t.setAssignedToId(q.getAssignedToId());
    if (q.getColorHex() != null)
	    t.setColorHex(q.getColorHex());
  }

  public int deleteTask(Query q) {
    Task deletedTask = getOne(q);
    int retval = deletedTask.getTaskId();
    allTasks.remove(deletedTask);
    return retval;
  }

  public int modifyTask(Query q) {
    Task modifiedTask = getOne(q);

    if (q.getTaskName() != null)
      modifiedTask.setTaskName(q.getTaskName());
    if (q.getTaskDesc() != null)
      modifiedTask.setTaskDesc(q.getTaskDesc());
    optionalTaskFields(q, modifiedTask);

    return modifiedTask.getTaskId();
  }

  public ArrayList<Task> getTasks(Query q) {
    ArrayList<Task> res = new ArrayList<Task>();
    for (Task task : allTasks) {
      if (verifyTaskMatchesQuery(task, q))
        res.add(task);
    }
    return res;
  }

  public boolean verifyTaskMatchesQuery(Task t, Query q) {
    boolean testID = (q.getTaskId() == 0) || ((q.getTaskId() != 0) && (t.getTaskId() == q.getTaskId()));
    boolean testName = (q.getTaskName() == null) || ((q.getTaskName() != null) && (t.getTaskName().equals(q.getTaskName())));
    boolean testCreatedBy = (q.getCreatedById() == 0) || ((q.getCreatedById() != 0) && (t.getCreatedById() == q.getCreatedById()));
    boolean testStatus = (q.getTaskStatus() == null) || ((q.getTaskStatus() != null) && (t.getTaskStatus().name().equals(q.getTaskStatus())));
    boolean testAssignedTo = (q.getAssignedToId() == 0) || ((q.getAssignedToId() != 0) && (t.getAssignedToId() == q.getAssignedToId()));
    boolean testColor = (q.getColorHex() == null) || ((q.getColorHex() != null) && (t.getColorHex().equals(q.getColorHex())));
    return (testID && testName && testCreatedBy && testStatus && testAssignedTo && testColor);
  }

  //AUXILIARY METHODS

  public ArrayList<User> getAllUsers() {
    return this.allUsers;
  }

}
