package com.anglesharp.app.roles;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Table(name = "role")
public class Roles {
  
  public enum Role {
    BETA,
    FREE,
    PREMIUM;
  }
  
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  private Role name;
  
  @ManyToOne
  private Roles parent;
  
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private Set<Roles> children;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Role getName() {
    return name;
  }

  public void setName(Role name) {
    this.name = name;
  }

  public Roles getParent() {
    return parent;
  }

  public void setParent(Roles parent) {
    this.parent = parent;
  }

  public Set<Roles> getChildren() {
    return children;
  }

  public void setChildren(Set<Roles> children) {
    this.children = children;
  }
  
}
