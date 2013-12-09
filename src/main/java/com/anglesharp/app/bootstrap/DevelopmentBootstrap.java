package com.anglesharp.app.bootstrap;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.core.User;
import com.anglesharp.app.db.DB;


public class DevelopmentBootstrap extends ApplicationBootstrap {

  public DevelopmentBootstrap(AppConfiguration config, DB db) {
    super(config, db);
  }

  @Override
  public void bootstrap() {
    System.out.println("Creating user...");
    if(db.users().all().size() == 0) {
      createUser();
    }
    
  }
  
  private void createUser() {
    User u = new User("test", "test");
    db.users().create(u);
  }

}
