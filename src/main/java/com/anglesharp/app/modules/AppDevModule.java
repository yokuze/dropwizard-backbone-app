package com.anglesharp.app.modules;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.db.DB;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class AppDevModule extends AbstractModule {
  
  private final DB db;
  private AppConfiguration config;
  
  public AppDevModule(DB db) {
    this.db = db;
  }
  
  public void setConfig(AppConfiguration config) {
    this.config = config;
  }
  
  @Override
  protected void configure() {
    // TODO
  }
  
  @Provides
  public DB provideDB() {
    return db;
  }
  
  @Provides
  public AppConfiguration provideAppConfiguration() {
    return config;
  }
  
}