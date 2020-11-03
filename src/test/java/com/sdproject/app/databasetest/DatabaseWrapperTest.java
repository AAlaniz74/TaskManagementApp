package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.database.Database;
import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.model.User;
import com.sdproject.app.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class DatabaseWrapperTest {

  @Test
  public void testInsertUser() {
    Database dummy = new DummyDatabase();
    DatabaseWrapper db = new DatabaseWrapper(dummy);
    int userID = db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    User searchedUser = db.query().tableIs("User").userIdIs(userID).getOne();
    assertEquals(searchedUser.getUserName(), "John"); 
  }
  
  @Test
  public void testDeleteUser() {
    DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
    db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").delete();
    ArrayList<User> searchedUsers = db.query().tableIs("User").userTypeIs("NORMAL").get();
    assertEquals(searchedUsers.size(), 0);
  }

  @Test
  public void testInsertTask() {
    DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
    int userID = db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    int taskID = db.query().tableIs("Task").taskNameIs("Test Task").taskDescIs("Test Description").createdByIdIs(userID).insert();
    Task searchedTask = db.query().tableIs("Task").taskIdIs(taskID).getOne();
    assertEquals(searchedTask.getTaskName(), "Test Task");
  }

  @Test
  public void testModifyTask() {
    DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
    int userID = db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    int taskID = db.query().tableIs("Task").taskNameIs("Test Task").taskDescIs("Test Description").createdByIdIs(userID).insert();
    db.query().tableIs("Task").taskIdIs(taskID).modifyTo().taskDescIs("New Desc").modify();
    Task searchedTask = db.query().tableIs("Task").taskIdIs(taskID).getOne();
    assertEquals(searchedTask.getTaskDesc(), "New Desc");
  }

  @Test
  public void testDeleteTask() {
    DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
    int userID = db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    int taskID = db.query().tableIs("Task").taskNameIs("Test Task").taskDescIs("Test Description").createdByIdIs(userID).insert();
    db.query().tableIs("Task").taskIdIs(taskID).delete();
    ArrayList<Task> searchedTasks = db.query().tableIs("Task").get();
    assertEquals(searchedTasks.size(), 0);
  }
}

