package com.sdproject.app.database;

import java.util.ArrayList;
import com.sdproject.app.model.Task;
import com.sdproject.app.model.User;

public class DatabaseWrapper {

  private Database db;
  private Query q;

  public DatabaseWrapper(Database db) {
    this.db = db;
  }

  public Query query() {
    q = new Query(this);
    return q;
  }

  public int insert(Query q) {
    return db.insert(q); 
  }

  public int delete(Query q) {
    return db.delete(q);
  }

  public int modify(Query q) {
    return db.modify(q);
  }

  public <T> ArrayList<T> get(Query q) {
    return (ArrayList<T>) db.get(q);
  }

  public <T> T getOne(Query q) {
    return (T) db.getOne(q);
  }

  public ArrayList<Task> getSubtasks(Query q) {
    return db.getSubtasks(q);
  }

  public ArrayList<User> getTeamMembers(Query q) {
    return db.getTeamMembers(q);
  }

  public void serializeAll() {
    db.serializeAll();
  }

  public void deserializeAll() {
    db.deserializeAll();
  }

  //ERROR CHECKING METHODS

  public boolean allUserFieldsSet(Query q) {
    return q.allUserFieldsSet();
  }
}
