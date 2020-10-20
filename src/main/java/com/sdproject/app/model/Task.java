package com.sdproject.app.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {

	private static int nextID = 701;

	private int taskID;
	private String taskName;
	private String taskDesc;
	private TaskStatus taskStatus;
	private ArrayList<Integer> subtaskIDs;
	private int assignedToID;
	private int createdByID;
	private Date createdOn;
	private Date dueDate;
	
	public Task(String name, String description, int createdByID) {
		this.taskName = name;
		this.taskDesc = description;
		this.createdByID = createdByID;
		this.createdOn = new Date();
		this.taskStatus = TaskStatus.IN_PROGRESS;
		this.taskID = nextID++;
	}

	public int getTaskID() {
		return this.taskID;
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

	public void addSubTaskID(int subtaskID) {
                subtaskIDs.add(subtaskID);
        }

        public ArrayList<Integer> getSubtaskIDs() {
                return this.subtaskIDs;
        }

        public void removeSubtaskID(int subtaskToRemove) {
                this.subtaskIDs.remove(subtaskToRemove);
        }

	public int getSubTaskSize(){
                return subtaskIDs.size();
        }

	public int getAssignedToID() {
                return this.assignedToID;
        }

        public void setAssignedToID(int newAssignedToID) {
                this.assignedToID = newAssignedToID;
        }

	public int getCreatedByID() {
                return this.createdByID;
        }

	public Date getCreatedOn() {
                return this.createdOn;
        }

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date newDate) {
		this.dueDate = newDate;
	}

}
