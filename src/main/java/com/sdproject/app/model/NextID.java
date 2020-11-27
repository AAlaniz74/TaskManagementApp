package com.sdproject.app.model;

import java.io.Serializable;

public class NextID implements Serializable {
  private int userNextID;
  private int taskNextID;
  private int teamNextID;

  public NextID(int userNextID, int taskNextID, int teamNextID) {
    this.userNextID = userNextID;
    this.taskNextID = taskNextID;
    this.teamNextID = teamNextID;
  }

  public int getUserNextID() {
    return this.userNextID;
  }

  public int getTaskNextID() {
    return this.taskNextID;
  }

  public int getTeamNextID() {
    return this.teamNextID;
  }
}
