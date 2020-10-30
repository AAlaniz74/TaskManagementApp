package com.sdproject.app.model;

import java.util.ArrayList;

public class Team {

  private static int nextID = 501;

  private int teamId;
  private String teamName;
  private ArrayList<Integer> users;
    
  public Team(String teamName) {
    this.teamName = teamName;
    this.users = new ArrayList<Integer>();
    this.teamId = nextID++;
  }
  
  public int getTeamId() {
    return this.teamId;
  }

  public String getTeamName() { return this.teamName; }
    
  public void setTeamName(String name) { this.teamName = name; }
    
  public ArrayList<Integer> getTeamMembers() {
    return this.users;
  }
    
  public int getTeamSize() {
    return users.size();
  }
    
  public void setMembers(ArrayList<Integer> users) { this.users = users; }
    
  public void addMember(int newMember) {
    users.add(newMember);
  }
    
  public void removeMember(int memberToRemove) {
    users.remove(memberToRemove);
  }
    
}

