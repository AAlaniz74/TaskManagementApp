package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class DummyDatabaseTest {

  @Test
  public void testInsertUser() {
    DummyDatabase db = new DummyDatabase();
    Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL");
    db.insert(insertUserQuery);
    ArrayList<User> allUsers = db.getAllUsers(); 
    assertEquals(allUsers.get(0).getUserName(), "John");
  }

  @Test
  public void testInsertAnotherUser() { 
    DummyDatabase db = new DummyDatabase();
    Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN");
    Query insertUserQuery2 = new Query().tableIs("User").userNameIs("James").userPassIs("Password").userTypeIs("NORMAL");
    db.insert(insertUserQuery);
    db.insert(insertUserQuery2);
    ArrayList<User> allUsers = db.getAllUsers();
    assertEquals(allUsers.get(1).getUserName(), "James");
  }

  @Test
  public void testSize() {
    DummyDatabase db = new DummyDatabase();
    Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN");
    Query insertUserQuery2 = new Query().tableIs("User").userNameIs("James").userPassIs("Password").userTypeIs("NORMAL");
    db.insert(insertUserQuery);
    db.insert(insertUserQuery2);
    assertEquals(db.getAllUsers().size(), 2);
  }

  @Test
  public void testSearchUserQuery() {
    DummyDatabase db = new DummyDatabase();
    Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN");
    Query insertUserQuery2 = new Query().tableIs("User").userNameIs("James").userPassIs("Password").userTypeIs("NORMAL");
    db.insert(insertUserQuery);
    db.insert(insertUserQuery2);
    Query searchUserQuery = new Query().tableIs("User").userTypeIs("ADMIN");
    ArrayList<User> searchedUsers = db.get(searchUserQuery);
    assertEquals(searchedUsers.get(0).getUserName(), "John");
  }

  @Test
  public void testDeleteUser() {
    DummyDatabase db = new DummyDatabase();
    Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN");
    db.insert(insertUserQuery);
    Query deleteUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN");
    db.delete(deleteUserQuery);
    assertEquals(db.getAllUsers().size(), 0);
  }

  @Test
  public void testModifyUser() {
    DummyDatabase db = new DummyDatabase();
    int userID = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN"));
    db.modify(new Query().tableIs("User").userIdIs(userID).modifyTo().userNameIs("Fred"));
    User searchedUser = db.getOne(new Query().tableIs("User").userIdIs(userID));
    assertEquals(searchedUser.getUserName(), "Fred");
  }

  @Test
  public void testInsertTaskWithSubtasks() {
    DummyDatabase db = new DummyDatabase();
    int userID = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("ADMIN"));
    int taskAID = db.insert(new Query().tableIs("Task").taskNameIs("Task A").taskDescIs("aaaaa").createdByIdIs(userID));
    int taskBID = db.insert(new Query().tableIs("Task").taskNameIs("Task B").taskDescIs("bbbbb").createdByIdIs(userID));
    ArrayList<Integer> subtasks = new ArrayList<Integer>( Arrays.asList(taskAID, taskBID));
    int superTaskID = db.insert(new Query().tableIs("Task").taskNameIs("SuperTask").taskDescIs("cccccc").createdByIdIs(userID).allSubtasksAre(subtasks));
    Task searchedTask = db.getOne(new Query().tableIs("Task").taskIdIs(superTaskID));
    assertEqual(searchedTask.getSubtaskIDs().size(), 2);
  }

}

