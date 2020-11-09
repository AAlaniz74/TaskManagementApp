package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class QueryTest {

  private Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL");
  private Query searchUserQuery = new Query().tableIs("User").userTypeIs("ADMIN");
  private Query modifyUserQuery = new Query().tableIs("User").userNameIs("John").modifyTo().userNameIs("Fred");

  @Test
  public void testIsTableSet() {
    assertEquals(new Query().isTableSet(), false);
  }

  @Test
  public void testAllUserFieldsSetComplete() {
    assertEquals(insertUserQuery.allUserFieldsSet(), true);
  }

  @Test
  public void testAllUserFieldsSetIncomplete() {
    assertEquals(searchUserQuery.allUserFieldsSet(), false);
  }

  @Test
  public void testUserTable() {
    assertEquals(insertUserQuery.getTable(), "User");
  }

  @Test
  public void testGetUserName() {
    assertEquals(insertUserQuery.getUserName(), "John");
  }

  @Test
  public void testGetUserPass() {
    assertEquals(insertUserQuery.getUserPass(), "Password");
  }

  @Test
  public void testGetUserType() {
    assertEquals(insertUserQuery.getUserType(), "NORMAL");
  }

  @Test
  public void testNullField() {
    assertEquals(searchUserQuery.getUserName(), null);
  }

  @Test
  public void testModifyFromName() {
    assertEquals(modifyUserQuery.getToModify().getUserName(), "John");
  }

  @Test
  public void testModifyToName() {
    assertEquals(modifyUserQuery.getUserName(), "Fred");
  }

  @Test
  public void testQueryTaskIDis() {
    Query q = new Query().tableIs("Task").taskIdIs(701);
    assertEquals(q.getTaskId(), 701);
  }

  @Test
  public void testQueryTaskNameis() {
    Query q = new Query().tableIs("Task").taskNameIs("Test Name");
    assertEquals(q.getTaskName(), "Test Name");
  }

  @Test
  public void testQueryTaskDescis() {
    Query q = new Query().tableIs("Task").taskDescIs("Test Desc");
    assertEquals(q.getTaskDesc(), "Test Desc");
  }

  @Test
  public void testQueryTaskStatusFinishedis() {
    Query q = new Query().tableIs("Task").taskStatusIs("FINISHED");
    assertEquals(q.getTaskStatus(), "FINISHED");
  }

  @Test
  public void testQueryTaskAllSubtasksAre() {
    ArrayList<Integer> subtasks = new ArrayList<Integer>();
    subtasks.add(1);
    subtasks.add(2);
    Query q = new Query().tableIs("Task").allSubtasksAre(subtasks);
    assertEquals(q.getSubtaskIDs().size(), 2);
  }

  @Test
  public void testQueryTaskAssignedtoIDis() {
    Query q = new Query().tableIs("Task").assignedToIdIs(101);
    assertEquals(q.getAssignedToId(), 101);
  }

  @Test
  public void testQueryTaskCreatedByIDis() {
    Query q = new Query().tableIs("Task").createdByIdIs(101);
    assertEquals(q.getCreatedById(), 101);
  }

  @Test
  public void testQueryTaskColorHexis() {
    Query q = new Query().tableIs("Task").colorHexIs("#FFFFFF");
    assertEquals(q.getColorHex(), "#FFFFFF");
  }

  @Test
  public void testQueryDueDateis() {
    Query q = new Query().tableIs("Task").dueDateIs("2020-03-09");
    assertEquals(q.getDueDate(), "2020-03-09");
  }

  @Test
  public void testQueryTaskRecurringDaysis() {
    Query q = new Query().tableIs("Task").recurringDaysIs(5);
    assertEquals(q.getRecurringDays(), 5);
  }

  @Test
  public void testQueryTeamIDis() {
    Query q = new Query().tableIs("Team").teamIdIs(501);
    assertEquals(q.getTeamId(), 501);
  }

  @Test
  public void testQueryTeamNameis() {
    Query q = new Query().tableIs("Team").teamNameIs("Team Name");
    assertEquals(q.getTeamName(), "Team Name");
  }

  @Test   
  public void testQueryTeamAllTeamMembersAre() {
    ArrayList<Integer> teamMember = new ArrayList<Integer>();
    teamMember.add(101);
    teamMember.add(102);
    Query q = new Query().tableIs("Team").allTeamMembersAre(teamMember);
    assertEquals(q.getTeamMemberIDs().size(), 2);
  }

}

