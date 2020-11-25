package com.sdproject.app.database;

import java.util.ArrayList;

import com.sdproject.app.model.Task;
import com.sdproject.app.model.User;

public interface Database {

  public int insert(Query q);
  public int delete(Query q); 
  public int modify(Query q);
  public <T> ArrayList<T> get(Query q);
  public <T> T getOne(Query q);
  public ArrayList<Task> getSubtasks(Query q);
  public ArrayList<User> getTeamMembers(Query q);
  public void serializeAll();
  public void deserializeAll();
}
