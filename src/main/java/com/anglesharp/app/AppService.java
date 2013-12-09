package com.anglesharp.app;

import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anglesharp.app.db.DB;
import com.anglesharp.app.db.OutboundEmailDAO;
import com.anglesharp.app.db.RoleDAO;
import com.anglesharp.app.db.UserDAO;
import com.anglesharp.app.modules.AppDevModule;
import com.google.common.collect.ImmutableList;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
import com.yammer.dropwizard.hibernate.SessionFactoryFactory;

public class AppService extends Service<AppConfiguration> {
  
  final Logger log = LoggerFactory.getLogger(AppService.class);
  
  private AppDevModule devModule;
  private DB db;
  private HibernateBundle<AppConfiguration> hibernate;
  
  public static void main(String[] args) throws Exception {
    new AppService().run(args);
  }
  
  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    bootstrap.setName("dw-app");
    
    ImmutableList<Class<?>> classes = scanForEntities();
    hibernate = new HibernateBundle<AppConfiguration>(classes, new SessionFactoryFactory()) {
      public DatabaseConfiguration getDatabaseConfiguration(AppConfiguration configuration) {
          return configuration.getDatabaseConfiguration();
      }
    };
    
    bootstrap.addBundle(hibernate);
    
    db = new DB();
    
    bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    
    devModule = new AppDevModule(db);
    bootstrap.addBundle(GuiceBundle.newBuilder()
                                   .addModule(devModule)
                                   .enableAutoConfig(getClass().getPackage().getName())
                                   .build());
  }

  @Override
  public void run(AppConfiguration configuration, Environment environment) throws Exception {

    OutboundEmailDAO emails = new OutboundEmailDAO(hibernate.getSessionFactory());
    UserDAO users = new UserDAO(hibernate.getSessionFactory());
    RoleDAO roles = new RoleDAO(hibernate.getSessionFactory());
    
    db.setEmails(emails);
    db.setUsers(users);
    db.setRoles(roles);
    
    devModule.setConfig(configuration);
  }
  
  private ImmutableList<Class<?>> scanForEntities() {
    log.info("Scanning " + this.getClass().getPackage().getName() + " for @Entity annotations");
    
    Reflections reflections = new Reflections(this.getClass().getPackage().getName());
    Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);
    
    log.info("Found " + classes.size() + " @Entity annotations");
    
    return ImmutableList.copyOf(classes);
  }
  
}