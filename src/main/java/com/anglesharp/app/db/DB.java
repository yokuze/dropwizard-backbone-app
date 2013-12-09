package com.anglesharp.app.db;


public class DB {
  
  private UserDAO users;
  private RoleDAO roles;
  private OutboundEmailDAO emails;

  public UserDAO users() {
    return users;
  }

  public void setUsers(UserDAO users) {
    this.users = users;
  }

  public RoleDAO roles() {
    return roles;
  }

  public void setRoles(RoleDAO roles) {
    this.roles = roles;
  }

  public OutboundEmailDAO emails() {
    return emails;
  }

  public void setEmails(OutboundEmailDAO emails) {
    this.emails = emails;
  }
  
}
