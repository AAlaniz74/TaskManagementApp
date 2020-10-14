package com.sdproject.app.database;

import java.util.ArrayList;

public class Query {

  private DatabaseWrapper wrap;

  //Specifies table
  private String table;

  //User attributes
  private String userName;
  private String userPass;
  private String userType;

  public Query() {}

  public Query(DatabaseWrapper wrap) {
    this.wrap = wrap;
  }

  public Query tableIs(String table) {
    this.table = table;
    return this;
  }

  public String getTable() {
    return this.table;
  }

  //USER QUERY

  public Query userNameIs(String name) {
    this.userName = name;
    return this;
  }

  public String getUserName() {
    return this.userName;
  }

  public Query userPassIs(String pass) {
    this.userPass = pass;
    return this;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public Query userTypeIs(String type) {
    this.userType = type;
    return this;
  }

  public String getUserType() {
    return this.userType;
  }

  //TERMINATING METHODS

  public void insert() {
    this.wrap.insert(this);
  }

  public <T> ArrayList<T> get() {
    return this.wrap.get(this);
  }
}

