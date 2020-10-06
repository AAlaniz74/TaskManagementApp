package com.sdproject.app.model;

public class User implements Assignable {

	public enum UserType {
		NORMAL,
		ADMIN
	}

	private String name;
	private UserType type;
	private static int countUsers;

	public User(String name, UserType type) {
		this.name = name;
		this.type = type;
		countUsers++;
	}

	public String getName() {
		return this.name;
	}

	public UserType getType() {
		return this.type;
	}

	public static int getCountUsers(){
		return countUsers;
	}

	public void removeUser(User user){
		user = null;
		countUsers--;
	}

}
