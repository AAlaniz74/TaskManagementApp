package com.sdproject.app;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.view.*;

import java.util.ArrayList;

public class App {
    
  public static void main( String[] args ) {
      DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
      db.deserializeAll();
      
      if (db.isDatabaseEmpty())
        seedDatabase(db);

      LoginView login = new LoginView(db);
  }

  public static void seedDatabase(DatabaseWrapper db) {
    int test = db.query().tableIs("User").userNameIs("John").userPassIs("Pass").userTypeIs("ADMIN").insert();
    int test2 = db.query().tableIs("User").userNameIs("Jack").userPassIs("Pass").userTypeIs("NORMAL").insert();
    db.query().tableIs("Task").taskNameIs("Test Task").taskDescIs("TESSSSST").createdByIdIs(test).insert();
    ArrayList<Integer> teamIDs = new ArrayList<Integer>();
    teamIDs.add(test);
    teamIDs.add(test2);
    db.query().tableIs("Team").teamNameIs("Test Team").allTeamMembersAre(teamIDs).insert();
  }

  public static void testRecurring(DatabaseWrapper db) {
    db.query().tableIs("Task").taskNameIs("Test Recurring").taskDescIs("Testing the recurring functionality").createdByIdIs(101).assignedToIdIs(101).dueDateIs("2020-12-03").recurringDaysIs(10).insert();
  }
}
