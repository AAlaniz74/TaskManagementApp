package com.sdproject.app.database;

import com.sdproject.app.model.User;
import com.sdproject.app.model.Task;

import java.util.ArrayList;

public class Query {

  private DatabaseWrapper wrap;

  //Specifies table
  private String table;

  //To Modify
  private Query toModify;

  //User attributes
  private int userId;
  private String userName;
  private String userPass;
  private String userType;

  //Task attributes
  private int taskId;
  private String taskName;
  private String taskDesc;
  private String taskStatus;
  private int assignedToId;
  private int createdById;
  private String colorHex;
  private String dueDate; //Use format "yyyy-MM-dd"
  private String completedOn; //Use format "yyyy-MM-dd"
  private int recurringDays; 
  private ArrayList<Integer> subtasks = new ArrayList<Integer>();

  // Team attributes
  private int teamId;
  private String teamName;
  private ArrayList<Integer> teamMembers = new ArrayList<Integer>();

  public Query() {}

  public Query(DatabaseWrapper wrap) {
    this.wrap = wrap;
  }

  public Query tableIs(String table) {
    this.table = table;
    return this;
  }

  public String getTable() {
    return this.table;
  }

  public Query modifyTo() {
    Query modifyQuery = new Query(this.wrap);
    return modifyQuery.tableIs(this.getTable()).toModifyIs(this);
  }

  private Query toModifyIs(Query toModify) {
    this.toModify = toModify;
    return this;
  }

  public Query getToModify() {
    return this.toModify;
  }

  public boolean isTableSet() {
    return (this.table != null);
  }
   
  //USER QUERY

  public Query userNameIs(String name) {
    this.userName = name;
    return this;
  }

  public String getUserName() {
    return this.userName;
  }

  public Query userIdIs(int userId) {
    this.userId = userId;
    return this;
  }

  public int getUserId() {
    return this.userId;
  }

  public Query userPassIs(String pass) {
    this.userPass = pass;
    return this;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public Query userTypeIs(String type) {
    this.userType = type;
    return this;
  }

  public String getUserType() {
    return this.userType;
  }

  public boolean allUserFieldsSet() {
    return (this.isTableSet()) && (this.getUserName() != null) && (this.getUserPass() != null) && (this.getUserType() != null);
  }

  //TASK QUERY
  
  public Query taskIdIs(int taskId) {
    this.taskId = taskId;
    return this;
  }

  public int getTaskId() {
    return this.taskId;
  }

  public Query taskNameIs(String taskName) {
    this.taskName = taskName;
    return this;
  }

  public String getTaskName() {
    return this.taskName;
  }

  public Query taskDescIs(String taskDesc) {
    this.taskDesc = taskDesc;
    return this;
  }

  public String getTaskDesc() {
    return this.taskDesc;
  }

  public Query taskStatusIs(String taskStatus) {
    this.taskStatus = taskStatus;
    return this;
  }

  public String getTaskStatus() {
    return this.taskStatus;
  }

  public Query allSubtasksAre(ArrayList<Integer> subtasks) {
    this.subtasks = subtasks;
    return this;
  }

  public ArrayList<Integer> getSubtaskIDs() {
    return this.subtasks;
  }

  public Query assignedToIdIs(int assignedToId) {
    this.assignedToId = assignedToId;
    return this;
  }

  public int getAssignedToId() {
    return this.assignedToId;
  }

  public Query createdByIdIs(int createdById) {
    this.createdById = createdById;
    return this;
  }

  public int getCreatedById() {
    return this.createdById;
  }

  public Query colorHexIs(String colorHex) {
    this.colorHex = colorHex;
    return this;
  }

  public String getColorHex() {
    return this.colorHex;
  }

  public Query dueDateIs(String dateString) {
    this.dueDate = dateString;
    return this;
  }

  public String getDueDate() {
    return this.dueDate;
  }

  public Query completedOnIs(String dateString) {
    this.completedOn = dateString;
    return this;
  }

  public String getCompletedOn() {
    return this.completedOn;
  }

  public Query recurringDaysIs(int days) {
    this.recurringDays = days;
    return this;
  }

  public int getRecurringDays() {
    return this.recurringDays;
  }

  // TEAM QUERY
  
  public Query teamIdIs(int teamId) {
    this.teamId = teamId;
    return this;
  }

  public int getTeamId() {
    return this.teamId;
  }
  
  public Query teamNameIs(String name) {
    this.teamName = name;
    return this;
  }

  public String getTeamName() {
    return this.teamName;
  }

  public Query teamMemberIs(int userID) {
    this.teamMembers.add(userID);
    return this;
  } 

  public Query allTeamMembersAre(ArrayList<Integer> users) {
    this.teamMembers = users;
    return this;
  }

  public ArrayList<Integer> getTeamMemberIDs() {
    return this.teamMembers;
  }


  //TERMINATING METHODS

  public int insert() {
    return this.wrap.insert(this);
  }

  public int delete() {
    return this.wrap.delete(this);
  }

  public int modify() {
    return this.wrap.modify(this);
  }

  public <T> ArrayList<T> get() {
    return (ArrayList<T>) this.wrap.get(this);
  }

  public <T> T getOne() {
    return (T) this.wrap.getOne(this);
  }

  public ArrayList<Task> getSubtasks() {
    return this.wrap.getSubtasks(this);
  }

  public ArrayList<User> getTeamMembers() {
    return this.wrap.getTeamMembers(this);
  }
}

