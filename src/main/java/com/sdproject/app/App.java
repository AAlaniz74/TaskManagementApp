package com.sdproject.app;

import com.sdproject.app.database.DatabaseWrapper;
import com.sdproject.app.database.DummyDatabase;
import com.sdproject.app.database.Query;
import com.sdproject.app.view.LoginView;

public class App
{
    public static void main( String[] args )
    {
      
	    
      DatabaseWrapper db = new DatabaseWrapper(new DummyDatabase());
      db.query().tableIs("User").userNameIs("Test").userPassIs("Pass").userTypeIs("NORMAL").insert();
      LoginView login = new LoginView(db);

    }
}
