package com.sdproject.app.model;

public class User {

	public enum UserType {
		NORMAL,
		ADMIN
	}

	private String name;
	private UserType type;

	public User(String name, UserType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public UserType getType() {
		return this.type;
	}

}
