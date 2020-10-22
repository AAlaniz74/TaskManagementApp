package com.sdproject.app.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {

	private static int nextID = 701;

	private int taskId;
	private String taskName;
	private String taskDesc;
	private TaskStatus taskStatus;
	private ArrayList<Integer> subtaskIDs;
	private int assignedToId;
	private int createdById;
	private Date createdOn;
	private Date dueDate;
	private String colorHex;

	public Task(String name, String description, int createdById) {
		this.taskName = name;
		this.taskDesc = description;
		this.createdById = createdById;
		this.createdOn = new Date();
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

	public Date getCreatedOn() {
                return this.createdOn;
        }

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date newDate) {
		this.dueDate = newDate;
	}

	public void setColorHex(String colorHex) {
		this.colorHex = colorHex;
	}

	public String getColorHex() {
		return this.colorHex;
	}

}
