package com.sdproject.app.database;

import java.util.ArrayList;

public class Query {

  private DatabaseWrapper wrap;

  //Specifies table
  private String table;

  //To Modify
  private Query toModify;

  //User attributes
  private int userID;
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

  public Query modifyTo() {
    Query modifyQuery = new Query(this.wrap);
    return modifyQuery.tableIs(this.getTable()).toModifyIs(this);
  }

  private Query toModifyIs(Query toModify) {
    this.toModify = toModify;
    return this;
  }

  public Query getToModify() {
    return this.toModify;
  }

  public boolean isTableSet() {
    return (this.table != null);
  }

  //USER QUERY

  public Query userNameIs(String name) {
    this.userName = name;
    return this;
  }

  public String getUserName() {
    return this.userName;
  }

  public Query userIDIs(int userID) {
    this.userID = userID;
    return this;
  }

  public int getUserID() {
    return this.userID;
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

  public boolean allUserFieldsSet() {
    return (this.isTableSet()) && (this.getUserName() != null) && (this.getUserPass() != null) && (this.getUserType() != null);
  }

  //TERMINATING METHODS

  public void insert() {
    this.wrap.insert(this);
  }

  public void delete() {
    this.wrap.delete(this);
  }

  public <T> ArrayList<T> get() {
    return this.wrap.get(this);
  }

  public void modify() {
    this.wrap.modify(this);
  }

}

