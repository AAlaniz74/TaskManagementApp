package com.sdproject.app.model;

import java.util.ArrayList;

public class Team implements Assignable {

	private ArrayList<User> users;

	public Team(ArrayList<User> users) {
		this.users = users;
	}

	public Team() {
		this.users = new ArrayList<User>();
	}

	public ArrayList<User> getTeamMembers() {
		return users;
	}	

	public void addMember(User newMember) {
		users.add(newMember);
	}

	public void removeMember(User memberToRemove) {
		users.remove(memberToRemove);
	}

}

