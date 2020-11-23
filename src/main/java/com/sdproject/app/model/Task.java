package com.sdproject.app.model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Task {

  private static int nextID = 701;

  private int taskId;
  private String taskName;
  private String taskDesc;
  private TaskStatus taskStatus;
  private ArrayList<Integer> subtaskIDs;
  private int assignedToId;
  private int createdById;
  private LocalDate createdOn;
  private LocalDate dueDate;
  private LocalDate completedOn;
  private int recurringDays;
  private String colorHex;

  public Task(String name, String description, int createdById) {
   this.taskName = name;
   this.taskDesc = description;
   this.createdById = createdById;
   this.assignedToId = createdById;
   this.createdOn = LocalDate.now();
   this.subtaskIDs = new ArrayList<Integer>();
   this.taskStatus = TaskStatus.IN_PROGRESS;
   this.taskId = nextID++;
  }

  public int getTaskId() {
    return this.taskId;
  }

  public String getTaskName() {
    return this.taskName;
  }

  public void setTaskName(String newName) {
    this.taskName = newName;
  }

  public String getTaskDesc() {
    return this.taskDesc;
  }

  public void setTaskDesc(String newDescription) {
    this.taskDesc = newDescription;
  }

  public TaskStatus getTaskStatus() {
    return this.taskStatus;
  }

  public void setTaskStatus(TaskStatus newStatus) {
    this.taskStatus = newStatus;
  }

  public void addSubTaskId(int subtaskId) {
    subtaskIDs.add(subtaskId);
  }

  public void setSubtaskIDs(ArrayList<Integer> subtasks) {
    this.subtaskIDs = subtasks;
  }

  public ArrayList<Integer> getSubtaskIDs() {
    return this.subtaskIDs;
  }

  public void removeSubtaskId(int subtaskToRemove) {
    this.subtaskIDs.remove(subtaskToRemove);
  }

  public int getSubTaskSize(){
    return subtaskIDs.size();
  }

  public int getAssignedToId() {
    return this.assignedToId;
  }

  public void setAssignedToId(int newAssignedToId) {
    this.assignedToId = newAssignedToId;
  }

  public int getCreatedById() {
    return this.createdById;
  }

  public LocalDate getCreatedOn() {
    return this.createdOn;
  }

  public LocalDate getDueDate() {
    return this.dueDate;
  }

  public void setDueDate(LocalDate newDate) {
    this.dueDate = newDate;
  }

  public LocalDate getCompletedOn() {
    return this.completedOn;
  }

  public void setCompletedOn(LocalDate newDate) {
    this.completedOn = newDate;
  }

  public void setRecurringDays(int days) {
    this.recurringDays = days;
  }

  public int getRecurringDays() {
    return this.recurringDays;
  }

  public void setColorHex(String colorHex) {
    this.colorHex = colorHex;
  }

  public String getColorHex() {
    return this.colorHex;
  }

  public Task copyTask() {
    Task copy = new Task(this.taskName, this.taskDesc, this.createdById);
    copy.setTaskStatus(this.taskStatus);
    copy.setSubtaskIDs(this.subtaskIDs);
    copy.setAssignedToId(this.assignedToId);
    copy.setDueDate(this.dueDate);
    copy.setRecurringDays(this.recurringDays);
    copy.setColorHex(this.colorHex);
    return copy;
  }
}
