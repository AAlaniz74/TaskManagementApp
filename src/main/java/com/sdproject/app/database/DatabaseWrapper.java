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

  public void insert(Query q) {
    db.insert(q); 
  }

  public void delete(Query q) {
    db.delete(q);
  }

  public <T> ArrayList<T> get(Query q) {
    return db.get(q);
  }

  public void modify(Query q) {
    db.modify(q);
  }

  //ERROR CHECKING METHODS

  public boolean allUserFieldsSet(Query q) {
    return q.allUserFieldsSet();
  }
}
