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

  public <T> ArrayList<T> get(Query q) {
    return db.get(q);
  }

}
