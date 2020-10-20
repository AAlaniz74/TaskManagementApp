package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest {

  //USER QUERY TESTS

  private Query insertUserQuery = new Query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL");
  private Query searchUserQuery = new Query().tableIs("User").userTypeIs("ADMIN");
  private Query modifyUserQuery = new Query().tableIs("User").userNameIs("John").modifyTo().userNameIs("Fred");

  @Test
  public void testIsTableSet() {
    assertEquals(new Query().isTableSet(), false);
  }

  @Test
  public void testAllUserFieldsSetComplete() {
    assertEquals(insertUserQuery.allUserFieldsSet(), true);
  }

  @Test
  public void testAllUserFieldsSetIncomplete() {
    assertEquals(searchUserQuery.allUserFieldsSet(), false);
  }

  @Test
  public void testUserTable() {
    assertEquals(insertUserQuery.getTable(), "User");
  }

  @Test
  public void testGetUserName() {
    assertEquals(insertUserQuery.getUserName(), "John");
  }

  @Test
  public void testGetUserPass() {
    assertEquals(insertUserQuery.getUserPass(), "Password");
  }

  @Test
  public void testGetUserType() {
    assertEquals(insertUserQuery.getUserType(), "NORMAL");
  }

  @Test
  public void testNullField() {
    assertEquals(searchUserQuery.getUserName(), null);
  }

  @Test
  public void testModifyFromName() {
    assertEquals(modifyUserQuery.getToModify().getUserName(), "John");
  }

  @Test
  public void testModifyToName() {
    assertEquals(modifyUserQuery.getUserName(), "Fred");
  }


}

