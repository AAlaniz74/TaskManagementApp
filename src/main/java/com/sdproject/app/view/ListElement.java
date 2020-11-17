package com.sdproject.app.view;

public class ListElement {
  String name;
  int ID;

  public ListElement(String name, int ID) {
    this.name = name;
    this.ID = ID;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
