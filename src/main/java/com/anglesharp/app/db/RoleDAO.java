package com.anglesharp.app.db;

import java.util.List;

import org.hibernate.SessionFactory;

import com.anglesharp.app.roles.Roles;
import com.anglesharp.app.roles.Roles.Role;


public class RoleDAO extends DAO<Roles> {

  public RoleDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
  
  public Roles find(Role role) {
    List<Roles> roles = findBy("name", role);
    if(roles.isEmpty())
      return null;
    
    return roles.get(0);
  }
}
