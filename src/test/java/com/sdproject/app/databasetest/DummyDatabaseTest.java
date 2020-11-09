package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.model.User;
import com.sdproject.app.model.UserType;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.model.Team;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class DummyDatabaseTest {
 
  @Test
  public void testInsertUserReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    assertNotEquals(insertVal, -1);
  }

  @Test
  public void testInsertTaskReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    assertNotEquals(insertVal, -1);
  }

  @Test
  public void testInsertTeamReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    assertNotEquals(insertVal, -1);
  }

  @Test
  public void testInsertUserSuccess() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    assertEquals(db.getAllUsers().size(), 1);
  }

  @Test
  public void testInsertTaskSuccess() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    assertEquals(db.getAllTasks().size(), 1);
  }

  @Test
  public void testInsertTeamSuccess() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    assertEquals(db.getAllTeams().size(), 1);
  }

  @Test
  public void testDeleteUserReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    int deleteVal = db.delete(new Query().tableIs("User").userIdIs(insertVal));
    assertNotEquals(deleteVal, -1);
  }

  @Test
  public void testDeleteTaskReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    int deleteVal = db.delete(new Query().tableIs("Task").taskIdIs(insertVal));
    assertNotEquals(deleteVal, -1);
  }

  @Test
  public void testDeleteTeamReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    int deleteVal = db.delete(new Query().tableIs("Team").teamIdIs(insertVal));
    assertNotEquals(deleteVal, -1);
  }

  @Test
  public void testDeleteUserSuccess() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    db.insert(new Query().tableIs("User").userNameIs("Jack").userPassIs("Password").userTypeIs("NORMAL"));
    db.delete(new Query().tableIs("User").userIdIs(insertVal));
    assertEquals(db.getAllUsers().size(), 1);
  }

  @Test
  public void testDeleteTaskSuccess() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    db.insert(new Query().tableIs("Task").taskNameIs("Task 2").taskDescIs("Desc").createdByIdIs(101));
    db.delete(new Query().tableIs("Task").taskIdIs(insertVal));
    assertEquals(db.getAllTasks().size(), 1);
  }

  @Test
  public void testDeleteTeamSuccess() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    db.insert(new Query().tableIs("Team").teamNameIs("Team 2"));
    db.delete(new Query().tableIs("Team").teamIdIs(insertVal));
    assertEquals(db.getAllTeams().size(), 1);
  }

  @Test
  public void testModifyUserReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    int modVal = db.modify(new Query().tableIs("User").userIdIs(insertVal).modifyTo().userNameIs("James"));
    assertNotEquals(modVal, -1);
  }

  @Test
  public void testModifyTaskReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    int modVal = db.modify(new Query().tableIs("Task").taskIdIs(insertVal).modifyTo().taskNameIs("New Task"));
    assertNotEquals(modVal, -1);
  }

  @Test
  public void testModifyTeamReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    int modVal = db.modify(new Query().tableIs("Team").teamIdIs(insertVal).modifyTo().teamNameIs("New Team"));
    assertNotEquals(modVal, -1);
  }

  @Test
  public void testGetUserReturn() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    db.insert(new Query().tableIs("User").userNameIs("John2").userPassIs("Password").userTypeIs("NORMAL"));
    ArrayList<User> searchedUsers = db.get(new Query().tableIs("User"));
    assertEquals(searchedUsers.size(), 2); 
  }

  @Test
  public void testGetTaskReturn() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    db.insert(new Query().tableIs("Task").taskNameIs("Task2").taskDescIs("Desc").createdByIdIs(101));
    ArrayList<Task> searchedTasks = db.get(new Query().tableIs("Task"));
    assertEquals(searchedTasks.size(), 2);
  }

  @Test
  public void testGetTeamReturn() {
    DummyDatabase db = new DummyDatabase();
    db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    db.insert(new Query().tableIs("Team").teamNameIs("Team 2"));
    ArrayList<Team> searchedTeams = db.get(new Query().tableIs("Team"));
    assertEquals(searchedTeams.size(), 2);
  }

  @Test
  public void testGetOneUserReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    db.insert(new Query().tableIs("User").userNameIs("John2").userPassIs("Password").userTypeIs("NORMAL"));
    User searchedUser = db.getOne(new Query().tableIs("User").userIdIs(insertVal));
    assertEquals(searchedUser.getUserName(), "John");
  }

  @Test
  public void testGetOneTaskReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Task").taskDescIs("Desc").createdByIdIs(101));
    db.insert(new Query().tableIs("Task").taskNameIs("Task2").taskDescIs("Desc").createdByIdIs(101));
    Task searchedTask = db.getOne(new Query().tableIs("Task").taskIdIs(insertVal));
    assertEquals(searchedTask.getTaskName(), "Task");
  }

  @Test
  public void testGetOneTeamReturn() {
    DummyDatabase db = new DummyDatabase();
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team"));
    db.insert(new Query().tableIs("Team").teamNameIs("Team 2"));
    Team searchedTeam = db.getOne(new Query().tableIs("Team").teamIdIs(insertVal));
    assertEquals(searchedTeam.getTeamName(), "Team");
  }

  @Test
  public void testGetSubtasks() {
    DummyDatabase db = new DummyDatabase();
    int insertValA = db.insert(new Query().tableIs("Task").taskNameIs("Subtask A").taskDescIs("Desc").createdByIdIs(101));
    int insertValB = db.insert(new Query().tableIs("Task").taskNameIs("Subtask B").taskDescIs("Desc").createdByIdIs(101));
    ArrayList<Integer> subtaskIDs = new ArrayList<Integer>();
    subtaskIDs.add(insertValA);
    subtaskIDs.add(insertValB);
    int insertVal = db.insert(new Query().tableIs("Task").taskNameIs("Supertask").taskDescIs("Desc").createdByIdIs(101).allSubtasksAre(subtaskIDs));
    ArrayList<Task> searchedSubtasks = db.getSubtasks(new Query().tableIs("Task").taskIdIs(insertVal));
    assertEquals(searchedSubtasks.size(), 2);
  }

  @Test
  public void testGetTeamMembers() {
    DummyDatabase db = new DummyDatabase();
    int insertValA = db.insert(new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL"));
    int insertValB = db.insert(new Query().tableIs("User").userNameIs("Jack").userPassIs("Password").userTypeIs("NORMAL"));
    ArrayList<Integer> teamMemberIDs = new ArrayList<Integer>();
    teamMemberIDs.add(insertValA);
    teamMemberIDs.add(insertValB);
    int insertVal = db.insert(new Query().tableIs("Team").teamNameIs("Team").allTeamMembersAre(teamMemberIDs));
    ArrayList<User> searchedTeamMembers = db.getTeamMembers(new Query().tableIs("Team").teamIdIs(insertVal));
    assertEquals(searchedTeamMembers.size(), 2);
  }

  @Test
  public void testVerifyUserMatches() {
    DummyDatabase db = new DummyDatabase();
    User newUser = new User("John", "Pass", UserType.ADMIN);
    Query userQuery = new Query().tableIs("User").userIdIs(newUser.getUserId()).userNameIs("John").userPassIs("Pass").userTypeIs("ADMIN");
    assertEquals(db.verifyUserMatchesQuery(newUser, userQuery), true);    
  }

  @Test
  public void testVerifyTaskMatches() {
    DummyDatabase db = new DummyDatabase();
    Task newTask = new Task("Task", "Desc", 101);
    newTask.setAssignedToId(101);
    newTask.setColorHex("#FFFFFF");
    Query taskQuery = new Query().tableIs("Task").taskIdIs(newTask.getTaskId()).taskNameIs("Task").createdByIdIs(101).taskStatusIs("IN_PROGRESS").assignedToIdIs(101).colorHexIs("#FFFFFF");
    assertEquals(db.verifyTaskMatchesQuery(newTask, taskQuery), true);
  }

  @Test
  public void testVerifyTeamMatches() {
    DummyDatabase db = new DummyDatabase();
    Team newTeam = new Team("Team");
    Query teamQuery = new Query().tableIs("Team").teamIdIs(newTeam.getTeamId()).teamNameIs("Team");
    assertEquals(db.verifyTeamMatchesQuery(newTeam, teamQuery), true);
  }

}

