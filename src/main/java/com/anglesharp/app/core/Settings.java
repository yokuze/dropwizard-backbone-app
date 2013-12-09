package com.anglesharp.app.core;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity @Table(name = "settings")
public class Settings {
  
  public enum Setting {
    // setting names go here
  }
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  private User user;
  
  @Index(name = "settings_name_index")
  @Enumerated(EnumType.STRING)
  private Setting name;
  
  private String value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Setting getName() {
    return name;
  }

  public void setName(Setting name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
  
}
