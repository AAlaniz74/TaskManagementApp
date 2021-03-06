package com.sdproject.app.database;

import com.sdproject.app.model.*;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Field;

public class DummyDatabase implements Database {
  private ArrayList<User> allUsers;
  private ArrayList<Task> allTasks;
  private ArrayList<Team> allTeams;

  public DummyDatabase() {
    this.allUsers = new ArrayList<User>();
    this.allTasks = new ArrayList<Task>();
    this.allTeams = new ArrayList<Team>();  
  }

  public int insert(Query q) {
    if (q.getTable().equals("User")) {
      return insertUser(q);
    } else if (q.getTable().equals("Task")) {
      return insertTask(q);
    } else if (q.getTable().equals("Team")) {
      return insertTeam(q);
    } else {
      return -1;
    }
  }

  public int delete(Query q) {
    if (q.getTable().equals("User")) {
      return deleteUser(q);
    } else if (q.getTable().equals("Task")) {
      return deleteTask(q);
    } else if (q.getTable().equals("Team")) {
      return deleteTeam(q);
    } else {
      return -1;
    }
  }

  public int modify(Query q) {
    if (q.getTable().equals("User")) {
      return modifyUser(q);
    } else if (q.getTable().equals("Task")) {
      return modifyTask(q);
    } else if (q.getTable().equals("Team")) {
      return modifyTeam(q);
    } else {
      return -1;
    }
  }

  public <T> ArrayList<T> get(Query q) {
    if (q.getTable().equals("User")) {
      return (ArrayList<T>) getUsers(q);
    } else if (q.getTable().equals("Task")) {
      return (ArrayList<T>) getTasks(q);
    } else if (q.getTable().equals("Team")) {
      return (ArrayList<T>) getTeams(q);
    }
    return null;
  }

  public <T> T getOne(Query q) {
    if (get(q).size() != 0)
      return (T) get(q).get(0);
    else
      return null;
  }

  public ArrayList<Task> getSubtasks(Query q) {
    Task searchedTask = getOne(q);
    ArrayList<Integer> subtaskIDs = searchedTask.getSubtaskIDs();
    ArrayList<Task> subtasks = new ArrayList<Task>();
    for (Integer id : subtaskIDs) {
      Task toAdd = getOne(new Query().tableIs("Task").taskIdIs(id));
      subtasks.add(toAdd);
    }

    return subtasks;
  }

  public ArrayList<User> getTeamMembers(Query q) {
    Team searchedTeam = getOne(q);
    ArrayList<Integer> userIDs = searchedTeam.getTeamMemberIDs();
    ArrayList<User> teamMembers = new ArrayList<User>();
    for (Integer id : userIDs) {
      User toAdd = getOne(new Query().tableIs("User").userIdIs(id));
      teamMembers.add(toAdd);
    }

    return teamMembers;
  }

  //USER METHODS

  public int insertUser(Query q) {
    User newUser = new User(q.getUserName(), q.getUserPass(), UserType.valueOf(q.getUserType()));
    allUsers.add(newUser);
    return newUser.getUserId();
  }

  public int deleteUser(Query q) {
    User deletedUser = getOne(q);
    int retval = deletedUser.getUserId();
    allUsers.remove(deletedUser);
    return retval;
  }

  public int modifyUser(Query q) {
    User modifiedUser = getOne(q.getToModify());

    if (q.getUserName() != null)
	    modifiedUser.setUserName(q.getUserName());
    if (q.getUserPass() != null)
            modifiedUser.setUserPass(q.getUserPass());
    if (q.getUserType() != null)
            modifiedUser.setUserType(UserType.valueOf(q.getUserType()));
    return modifiedUser.getUserId();
  }

  public boolean checkNoDuplicateUser(Query q) {
    Query dupeCheck = new Query().tableIs(q.getTable()).userNameIs(q.getUserName());
    ArrayList<User> searchedUser = getUsers(dupeCheck);
    return (searchedUser.size() == 0);
  }

  public ArrayList<User> getUsers(Query q) {
    ArrayList<User> res = new ArrayList<User>();
    updateAllUserProductivity();
    for (User user : allUsers) {
      if (verifyUserMatchesQuery(user, q))
        res.add(user);
    }
    return res;
  }

  public boolean verifyUserMatchesQuery(User user, Query q) {
    boolean testName = (q.getUserName() == null) || ((q.getUserName() != null) && user.getUserName().equals(q.getUserName()));
    boolean testPass = (q.getUserPass() == null) || ((q.getUserPass() != null) && user.getUserPass().equals(q.getUserPass()));
    boolean testType = (q.getUserType() == null) || ((q.getUserType() != null) && user.getUserType().name().equals(q.getUserType()));
    boolean testID = (q.getUserId() == 0) || ((q.getUserId() != 0) && (user.getUserId() == q.getUserId()));
    return (testName && testPass && testType && testID);
  }

  //TASK METHODS

  public int insertTask(Query q) {
    Task newTask = new Task(q.getTaskName(), q.getTaskDesc(), q.getCreatedById());
    optionalTaskFields(q, newTask);
    this.allTasks.add(newTask);
    return newTask.getTaskId();
  }

  private void optionalTaskFields(Query q, Task t) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    if (q.getTaskStatus() != null)
	    t.setTaskStatus(TaskStatus.valueOf(q.getTaskStatus()));
    if (q.getAssignedToId() != 0)
	    t.setAssignedToId(q.getAssignedToId());
    if (q.getColorHex() != null)
	    t.setColorHex(q.getColorHex());
    if (q.getDueDate() != null)
      t.setDueDate(LocalDate.parse(q.getDueDate(), formatter));  
    if (q.getCompletedOn() != null)
      t.setCompletedOn(LocalDate.parse(q.getCompletedOn(), formatter));
    if (q.getRecurringDays() != 0)
      t.setRecurringDays(q.getRecurringDays());
    if (q.getSubtaskIDs().size() > 0)
      t.setSubtaskIDs(q.getSubtaskIDs());
  }

  public int deleteTask(Query q) {
    Task deletedTask = getOne(q);
    int retval = deletedTask.getTaskId();
    allTasks.remove(deletedTask);
    return retval;
  }

  public int modifyTask(Query q) {
    Task modifiedTask = getOne(q.getToModify());

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
      if (verifyTaskMatchesQuery(task, q)) {
        res.add(task);
      }
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

  // TEAM METHODS

  public int insertTeam(Query q) {
    Team newTeam = new Team(q.getTeamName());
    
    if (q.getTeamMemberIDs() != null && q.getTeamMemberIDs().size() > 0) {
      newTeam.setTeamMemberIDs(q.getTeamMemberIDs());
    }

    allTeams.add(newTeam);
    return newTeam.getTeamId();
  }

  public ArrayList<Team> getTeams(Query q) {
    ArrayList<Team> res = new ArrayList<Team>();
    for (Team team : allTeams) {
      if (verifyTeamMatchesQuery(team, q))
        res.add(team);
    }
    return res;
  }

  public int deleteTeam(Query q) {
    Team deletedTeam = getOne(q);
    int retval = deletedTeam.getTeamId();
    allTeams.remove(deletedTeam);
    return retval;
  }

  public int modifyTeam(Query q) {
    Team modifiedTeam = getOne(q.getToModify());
    
    if (q.getTeamName() != null)
      modifiedTeam.setTeamName(q.getTeamName());
    if (q.getTeamMemberIDs() != null && q.getTeamMemberIDs().size() > 0) {
      modifiedTeam.setTeamMemberIDs(q.getTeamMemberIDs());
    }

    return modifiedTeam.getTeamId();
  }

  public boolean verifyTeamMatchesQuery(Team t, Query q) {
    boolean testID = (q.getTeamId() == 0) || ((q.getTeamId() != 0) && (t.getTeamId() == q.getTeamId()));
    boolean testName = (q.getTeamName() == null) || ((q.getTeamName() != null) && (t.getTeamName().equals(q.getTeamName())));
    return (testID && testName);
  }

  //AUXILIARY METHODS

  public ArrayList<User> getAllUsers() {
    return this.allUsers;
  }

  public ArrayList<Task> getAllTasks() {
    return this.allTasks;
  }

  public ArrayList<Team> getAllTeams() {
    return this.allTeams;
  }

  public boolean isDatabaseEmpty() {
    boolean userTest = (getAllTeams().size() == 0);
    boolean taskTest = (getAllTasks().size() == 0);
    boolean teamTest = (getAllTeams().size() == 0);
    return userTest && taskTest && teamTest;
  }

  public void setAllUsers(ArrayList<User> newUsers) {
    this.allUsers = newUsers;
  }

  public void setAllTasks(ArrayList<Task> newTasks) {
    this.allTasks = newTasks;
  }

  public void setAllTeams(ArrayList<Team> newTeams) {
    this.allTeams = newTeams;
  }

  public void updateRecurring() {
    ArrayList<Task> newRecurringTasks = new ArrayList<Task>();
    for (Task task : this.allTasks) {
      if (task.getRecurringDays() != 0 && task.getActiveRecurring() && LocalDate.now().isAfter(task.getDueDate())) {
        if (task.getTaskStatus() != TaskStatus.FINISHED)
          task.setTaskStatus(TaskStatus.PAST_DUE);
        Task recurringTask = task.copyTask();
        recurringTask.setDueDate(task.getDueDate().plusDays(task.getRecurringDays()));
        recurringTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        recurringTask.setCreatedOn(LocalDate.now());
        recurringTask.setActiveRecurring(true);

        newRecurringTasks.add(recurringTask);
        task.setActiveRecurring(false);        
      }
    }
    this.allTasks.addAll(newRecurringTasks);
  }

  public void updateUserProductivity(User user) {
    int count = 0;
    double newScore = 1.00;
    ArrayList<Task> userTasks = get(new Query().tableIs("Task").assignedToIdIs(user.getUserId()).taskStatusIs("FINISHED"));

    for (Task task : userTasks) {
      if (task.getDueDate() != null) {
        double possibleDays = (double) ChronoUnit.DAYS.between(task.getCreatedOn(), task.getDueDate());
        double actualDays = (double) ChronoUnit.DAYS.between(task.getCompletedOn(), task.getDueDate());

        if (possibleDays != 0) {
          newScore += (actualDays/possibleDays);
          count++;
        }
      }
    }
    
    if (count > 0)
      user.setUserProductivity(newScore/count);
  }

  public void updateAllUserProductivity() {
    for (User user : allUsers) {
      updateUserProductivity(user);
    }
  }

  // SERIALIZATION METHODS

  public void serializeObj(String filename, Object obj) {
    try {
      FileOutputStream fos = new FileOutputStream(filename);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(obj);
      oos.close();
      fos.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private static int getNextIdField(Class<?> clazz) {
    try {
      Field field = clazz.getDeclaredField("nextID");
      field.setAccessible(true);
      return (int) field.get(null);
    } catch (ReflectiveOperationException ex) {
      System.out.println(ex.getMessage());
    }
    return 0;
  }

  public void serializeAll() {
    checkIfSerExists();
    serializeObj("ser/user.ser", this.allUsers);
    serializeObj("ser/task.ser", this.allTasks);
    serializeObj("ser/team.ser", this.allTeams);

    int userCurrentNextID = getNextIdField(User.class);
    int taskCurrentNextID = getNextIdField(Task.class);
    int teamCurrentNextID = getNextIdField(Team.class);

    NextID nextID = new NextID(userCurrentNextID, taskCurrentNextID, teamCurrentNextID);
    serializeObj("ser/next_id.ser", nextID);
  }

  public <T> T deserializeObj(String filename) {
    try {
      T obj;
      FileInputStream fis = new FileInputStream(filename);
      ObjectInputStream ois = new ObjectInputStream(fis);
      obj = (T) ois.readObject();
      ois.close();
      fis.close();
      return obj;
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch (ClassNotFoundException cex) {
      System.out.println(cex.getMessage());
    }
    return null;
  }

  private void setNextID(Class<?> clazz, int nextID) {
    try {
      Field field = clazz.getDeclaredField("nextID");
      field.setAccessible(true);
      field.set(null, nextID);
    } catch (ReflectiveOperationException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void checkIfSerExists() {  
    File dir = new File("ser");
    if (!dir.exists()) {
      dir.mkdirs();
    }
  }

  public void deserializeAll() {
    ArrayList<User> newUsers = deserializeObj("ser/user.ser");
    ArrayList<Task> newTasks = deserializeObj("ser/task.ser");
    ArrayList<Team> newTeams = deserializeObj("ser/team.ser");
    NextID nextID = deserializeObj("ser/next_id.ser");

    if (newUsers != null)
      setAllUsers(newUsers);
    if (newTasks != null)
      setAllTasks(newTasks);
    if (newTeams != null)
      setAllTeams(newTeams);
    if (nextID != null) {
      setNextID(User.class, nextID.getUserNextID());
      setNextID(Task.class, nextID.getTaskNextID());
      setNextID(Team.class, nextID.getTeamNextID());
    }

    updateRecurring();
  }

}
