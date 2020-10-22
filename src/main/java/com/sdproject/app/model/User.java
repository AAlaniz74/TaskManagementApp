package com.sdproject.app.model;

public class User {

	private static int nextID = 101;

	private int userId;
	private String userName;
	private String userPass;
	private UserType userType;

	public User(String name, String password, UserType type) {
		this.userName = name;
		this.userPass = password;
		this.userType = type;
		this.userId = nextID++;
	}

	public int getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String newName) {
		this.userName = newName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String newPass) {
		this.userPass = newPass;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(UserType newType) {
		this.userType = newType;
	}

}
