package com.sdproject.app.databasetest;

import com.sdproject.app.database.Query;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.database.Database;
import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class DatabaseWrapperTest {

  @Test
  public void testInsertUser() {
    Database dummy = new DummyDatabase();
    DatabaseWrapper db = new DatabaseWrapper(dummy);
    db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    ArrayList<User> searchedUsers = db.query().tableIs("User").userTypeIs("NORMAL").get();
    assertEquals(searchedUsers.get(0).getUserName(), "John");
  }
  
  @Test
  public void testDeleteUser() {
    DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
    db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").insert();
    db.query().tableIs("User").userNameIs("John").userPassIs("Password").userTypeIs("NORMAL").delete();
    ArrayList<User> searchedUsers = db.query().tableIs("User").userTypeIs("NORMAL").get();
    assertEquals(searchedUsers.size(), 0);
  }
}

