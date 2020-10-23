package com.sdproject.app.model;

import java.util.ArrayList;

public class Team {
    
    private String teamName;
    private ArrayList<User> users;
    
    public Team(String newTeamName, ArrayList<User> users) {
        this.teamName = newTeamName;
        this.users = users;
    }
    
    public Team() {
        this.teamName = "";
        this.users = new ArrayList<User>();
    }
    
    public String getTeamName() { return this.teamName; }
    
    public void setTeamName(String name) { this.teamName = name; }
    
    public ArrayList<User> getTeamMembers() {
        return users;
    }
    
    public int getTeamSize() {
        return users.size();
    }
    
    public void setMembers(ArrayList<User> users) { this.users = users; }
    
    public void addMember(User newMember) {
        users.add(newMember);
    }
    
    public void removeMember(User memberToRemove) {
        users.remove(memberToRemove);
    }
    
    public void removeTeam(Team team) { team = null; }
    
}

