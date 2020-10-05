package com.sdproject.app.model;

import java.util.ArrayList;
import java.util.Date;

public class TaskCatagory {

    private String name;
    private String description;
    private ArrayList<Task> taskList;
    private User createdBy;
    private Date createdOn;
    private static int countTasks;

    TaskCatagory(String name, String description, ArrayList<Task> taskList, User createdBy) {
        this.name = name;
        this.description = description;
        this.taskList = taskList;
        this.createdBy = createdBy;
        this.createdOn = new Date();
        countTasks++;
    }
    public void addTask(Task task) {
        taskList.add(task);
    }
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }
    public void setSubtasks(ArrayList<Task> newTaskList) { this.taskList = newTaskList; }
    public String getName() {
        return this.name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
    public User getCreatedBy() {
        return this.createdBy;
    }
    public Date getCreatedOn() {
        return this.createdOn;
    }
    public static int getCountTasks() { return countTasks; }
}
