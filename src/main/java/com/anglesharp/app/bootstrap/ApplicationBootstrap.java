package com.anglesharp.app.bootstrap;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.db.DB;

public abstract class ApplicationBootstrap {
  
  protected final AppConfiguration config;
  protected final DB db;
  
  public ApplicationBootstrap(AppConfiguration config, DB db) {
    this.db = db;
    this.config = config;
  }
  
  public abstract void bootstrap();
  
  public static ApplicationBootstrap create(AppConfiguration config, DB db) {
    return config.isDev() ? new DevelopmentBootstrap(config, db) : new ProdBootstrap(config, db);
  }
}
