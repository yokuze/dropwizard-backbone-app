package com.anglesharp.app.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.criterion.Restrictions;

import io.dropwizard.hibernate.AbstractDAO;

public class DAO<T> extends AbstractDAO<T> {
  
  protected SessionFactory sessionFactory;
  
  public DAO(SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }
  
  
  public T create(T t) {
    currentSession().save(t);
    return t;
  }
  
  public T save(T t) {
    currentSession().update(t);
    return t;
  }
  
  public void delete(T t) {
     currentSession().delete(t);
  }
  
  public T find(Long id) {
    return super.get(id);
  }
  
  @SuppressWarnings("unchecked")
  public List<T> findBy(String field, Object value) {
    Criteria c = criteria().add(Restrictions.eq(field, value));
    return c.list();
  }
  
  public long count() {
    String entityName = getEntityClass().getSimpleName();
    return (Long)currentSession()
           .createQuery("select count(" + entityName.charAt(0) + ") from " + 
                        entityName + " " + entityName.charAt(0))
           .uniqueResult();
  }
  
  @SuppressWarnings("unchecked")
  public List<T> all() {
    return criteria().list();
  }
  
  @SuppressWarnings("unchecked")
  public List<T> list(int offset, int pageSize) {
    return criteria().setMaxResults(pageSize).setFirstResult(offset).list();
  }
  
  public void startSession() {
    ManagedSessionContext.bind(sessionFactory.openSession());
    sessionFactory.getCurrentSession().beginTransaction();
  }
  
  public void startTransaction() {
    if(sessionFactory.getCurrentSession().getTransaction().isActive())
      return;
    
    sessionFactory.getCurrentSession().beginTransaction();
  }
  
  public void endTransaction() {
    if(!sessionFactory.getCurrentSession().getTransaction().isActive())
      return;
    
    sessionFactory.getCurrentSession().getTransaction().commit();
  }
  
  public void endSession() {
    
    if(!sessionFactory.getCurrentSession().isOpen())
      return;
    
    sessionFactory.getCurrentSession().close();
    ManagedSessionContext.unbind(sessionFactory);
  }
  
  public void flush() {
    if(!sessionFactory.getCurrentSession().isOpen())
      return;
    
    sessionFactory.getCurrentSession().flush();
  }
  
  public void clear() {
    sessionFactory.getCurrentSession().clear();
  }
}
