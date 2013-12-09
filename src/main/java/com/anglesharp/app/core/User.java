package com.anglesharp.app.core;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.anglesharp.app.core.Settings.Setting;
import com.anglesharp.app.roles.Roles;
import com.anglesharp.app.roles.Roles.Role;
import com.anglesharp.app.util.PasswordHash;
import com.anglesharp.app.util.UUIDUtil;

@Entity @Table(name = "user")
public class User {
  
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Index(name = "user_email")
  private String email;
  private String password;
  private Date created;
  private Date lastLogin;
  
  private String passwordResetCode;
  private String emailVerificationCode;
  
  private boolean emailVerified;
  
  @ManyToOne
  private Roles role;
  
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Settings> settings;
  
  @Transient
  private String roleName;
  
  private Date deleted;
  
  public User() {
    this.created = new Date();
  }
  
  public User(String email) {
    this.passwordResetCode = UUIDUtil.code();
    this.emailVerificationCode = UUIDUtil.code();
    this.email = email;
    this.created = new Date();
  }
  
  public User(String email, String password) {
    this.passwordResetCode = UUIDUtil.code();
    this.emailVerificationCode = UUIDUtil.code();
    this.email = email;
    this.password = PasswordHash.hash(password);
    this.created = new Date();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getPasswordResetCode() {
    return passwordResetCode;
  }
  public void setPasswordResetCode(String passwordResetCode) {
    this.passwordResetCode = passwordResetCode;
  }
  public String getEmailVerificationCode() {
    return emailVerificationCode;
  }

  public void setEmailVerificationCode(String emailVerificationCode) {
    this.emailVerificationCode = emailVerificationCode;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }
  
  
  public Roles getRole() {
    return role;
  }

  public void setRole(Roles role) {
    this.role = role;
  }

  public Set<Settings> getSettings() {
    return settings;
  }

  public void setSettings(Set<Settings> settings) {
    this.settings = settings;
  }
  
  public String getSetting(Setting setting) {
    for(Settings s: settings) {
      if(s.getName() == setting)
        return s.getValue();
    }
    
    return "";
  }
  
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Date getDeleted() {
    return deleted;
  }

  public void setDeleted(Date deleted) {
    this.deleted = deleted;
  }

  public boolean authenticate(String plaintextPassword) {
    return PasswordHash.check(plaintextPassword, password);
  }
  
  public boolean hasRole(Role name) {
    if(this.role == null || name == null)
      return false;
    
    return containsRole(this.role, name);
  }
  
  private boolean containsRole(Roles role, Role name) {
    if(role.getName() == name)
      return true;
    
    for(Roles r : role.getChildren()) {
      if(containsRole(r, name))
        return true;
    }
    
    return false;
  }
}
