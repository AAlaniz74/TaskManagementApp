package com.sdproject.app.view;

public class ColorItem {
  String colorName;
  String colorHex;

  public ColorItem(String colorName, String colorHex) {
    this.colorName = colorName;
    this.colorHex = colorHex;
  }

  @Override
  public String toString() {
    return this.colorName;
  }
}
