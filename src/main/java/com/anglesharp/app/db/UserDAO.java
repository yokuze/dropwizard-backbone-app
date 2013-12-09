package com.anglesharp.app.db;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.anglesharp.app.core.User;


public class UserDAO extends DAO<User> {

  public UserDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
  
  public User findByEmail(String email) {
      return (User) criteria()
             .add(Restrictions.eq("email", email))
             .uniqueResult();
  }
}
