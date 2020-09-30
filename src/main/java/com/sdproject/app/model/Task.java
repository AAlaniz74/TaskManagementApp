package com.sdproject.app.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {

	enum Status {
		FINISHED,
		IN_PROGRESS
	}

	private String name;
	private String description;
	private ArrayList<Task> subtasks;
	private Assignable assignedTo;
	private Date dueDate;
	private Status status;
	private User createdBy;
	private Date createdOn;

	Task(String name, String description, Date dueDate, User createdBy) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.createdBy = createdBy;
		this.status = Status.IN_PROGRESS;
		this.createdOn = new Date();
	}

	public void addSubtasks(ArrayList<Task> subtasks) {
		this.subtasks = subtasks;
	}

	public ArrayList<Task> getSubtasks() {
		return this.subtasks;
	}

	public void setSubtasks(ArrayList<Task> newSubtasks) {
		this.subtasks = newSubtasks;
	}

	public void addAssignedTo(Assignable assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Assignable getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(Assignable newAssignedTo) {
		this.assignedTo = newAssignedTo;
	}

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

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date newDate) {
		this.dueDate = newDate;
	}

	public User getCreatedBy() {
		return this.createdBy;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status newStatus) {
		this.status = newStatus;
	}
}
