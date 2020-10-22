package com.sdproject.app.database;

import java.util.ArrayList;

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

  public <T> ArrayList<T> get(Query q) {
    return db.get(q);
  }

  public <T> T getOne(Query q) {
    return db.getOne(q);
  }

  public int modify(Query q) {
    return db.modify(q);
  }

  //ERROR CHECKING METHODS

  public boolean allUserFieldsSet(Query q) {
    return q.allUserFieldsSet();
  }
}
