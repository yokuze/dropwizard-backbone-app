package com.anglesharp.app.db;

import org.hibernate.SessionFactory;

import com.anglesharp.app.core.OutboundEmail;

public class OutboundEmailDAO extends DAO<OutboundEmail> {

  public OutboundEmailDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
