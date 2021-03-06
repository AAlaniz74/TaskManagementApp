package com.sdproject.app.model;

import java.io.Serializable;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;

	private static int nextID = 101;
	private int userId;
	private String userName;
	private String userPass;
	private UserType userType;
  private double userProductivity;

	public User(String name, String password, UserType type) {
		this.userName = name;
		this.userPass = password;
		this.userType = type;
		this.userId = nextID++;
    this.userProductivity = 1.00;
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

  public double getUserProductivity() {
    return this.userProductivity;
  }

  public void setUserProductivity(double prod) {
    this.userProductivity = prod;
  }

}
