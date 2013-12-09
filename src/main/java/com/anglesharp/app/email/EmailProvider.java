package com.anglesharp.app.email;

import org.apache.commons.mail.EmailException;

import com.anglesharp.app.AppConfiguration.EmailConfiguration;
import com.anglesharp.app.core.OutboundEmail;


public abstract class EmailProvider {
  
  protected final EmailConfiguration configuration;
  
  public EmailProvider(EmailConfiguration configuration) {
    this.configuration = configuration;
  }
  
  public abstract OutboundEmail send(OutboundEmail email) throws EmailFailedException;
  
  public static class EmailFailedException extends Exception {
    public EmailFailedException(EmailException e) {
      super(e);
    }

    private static final long serialVersionUID = 1L;
    
  }
  
}