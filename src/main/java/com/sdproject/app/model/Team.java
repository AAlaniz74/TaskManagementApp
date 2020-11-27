package com.sdproject.app.model;

import java.util.ArrayList;
import java.io.Serializable;

public class Team implements Serializable {

  private static final long serialVersionUID = 1L;
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

  public String getTeamName() { 
    return this.teamName; 
  }
    
  public void setTeamName(String name) { 
    this.teamName = name; 
  }
    
  public ArrayList<Integer> getTeamMemberIDs() {
    return this.users;
  }
    
  public int getTeamSize() {
    return users.size();
  }
    
  public void setTeamMemberIDs(ArrayList<Integer> users) { 
    this.users = users; 
  }
    
  public void addMember(int newMember) {
    users.add(newMember);
  }
    
  public void removeMember(int memberToRemove) {
    users.remove(memberToRemove);
  }

}

