package com.sdproject.app.model;

public class User implements Assignable {

	private String userName;
	private String userPass;
	private UserType userType;
	private static int countUsers;

	public User(String name, String password, UserType type) {
		this.userName = name;
		this.userPass = password;
		this.userType = type;
		countUsers++;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public static int getCountUsers(){
		return countUsers;
	}

	public void removeUser(User user){
		user = null;
		countUsers--;
	}

}
