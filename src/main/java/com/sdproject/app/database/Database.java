package com.sdproject.app.database;

import java.util.ArrayList;

public interface Database {

  public int insert(Query q);
  public int delete(Query q);
  public <T> ArrayList<T> get(Query q);
  public <T> T getOne(Query q);
  public int modify(Query q);
}
