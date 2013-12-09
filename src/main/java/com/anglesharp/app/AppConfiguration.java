package com.anglesharp.app;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public class AppConfiguration extends Configuration {
  
  private EmailConfiguration email;
  
  private boolean dev = true;
  
  @NotNull
  private String rootUrl;
  
  @Valid
  @NotNull
  @JsonProperty
  private DatabaseConfiguration database = new DatabaseConfiguration();

  public DatabaseConfiguration getDatabaseConfiguration() {
      return database;
  }
  
  public EmailConfiguration getEmail() {
    return email;
  }
  
  public void setEmail(EmailConfiguration email) {
    this.email = email;
  }
  
  public boolean isDev() {
    return dev;
  }

  public void setDev(boolean dev) {
    this.dev = dev;
  }

  public String getRootUrl() {
    return rootUrl;
  }

  public void setRootUrl(String rootUrl) {
    this.rootUrl = rootUrl;
  }

  public static class EmailConfiguration {
    
    private String provider;
    
    private String host;
    private String username;
    private String password;
    private String protocol;
    private String port;
    private String from;
    
    public String getProvider() {
      return provider;
    }
    public void setProvider(String provider) {
      this.provider = provider;
    }
    public String getHost() {
      return host;
    }
    public void setHost(String host) {
      this.host = host;
    }
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public String getProtocol() {
      return protocol;
    }
    public void setProtocol(String protocol) {
      this.protocol = protocol;
    }
    public String getPort() {
      return port;
    }
    public void setPort(String port) {
      this.port = port;
    }
    public String getFrom() {
      return from;
    }
    public void setFrom(String from) {
      this.from = from;
    }
  }
}