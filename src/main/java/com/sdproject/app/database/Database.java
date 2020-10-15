package com.sdproject.app.database;

import java.util.ArrayList;

public interface Database {

  public void insert(Query q);
  public void delete(Query q);
  public <T> ArrayList<T> get(Query q);
  public void modify(Query q);
}
