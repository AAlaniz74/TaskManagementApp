package com.sdproject.app;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.database.Query;
import com.sdproject.app.model.TaskStatus;
import com.sdproject.app.view.*;

public class App
{
    public static void main( String[] args )
    {
      DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
      db.query().tableIs("User").userNameIs("Test").userPassIs("Pass").userTypeIs("NORMAL").insert();
      db.query().tableIs("Team").teamNameIs("Name").teamMembersAre(db.query().tableIs("User").get()).insert();
      db.query().tableIs("Task").taskNameIs("NewTest").taskIdIs(1).taskDescIs("This is a test").insert();
      LoginView login = new LoginView(db);
    }
}
