package com.anglesharp.app.bootstrap;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.db.DB;

public class ProdBootstrap extends ApplicationBootstrap {

  public ProdBootstrap(AppConfiguration config, DB db) {
    super(config, db);
  }

  @Override
  public void bootstrap() {
    
  }
  
}
