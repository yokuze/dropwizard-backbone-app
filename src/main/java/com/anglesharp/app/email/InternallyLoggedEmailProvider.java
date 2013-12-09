package com.anglesharp.app.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anglesharp.app.AppConfiguration.EmailConfiguration;
import com.anglesharp.app.core.OutboundEmail;

public class InternallyLoggedEmailProvider extends EmailProvider {

  private static final Logger log  = LoggerFactory.getLogger(InternallyLoggedEmailProvider.class);
  
  public InternallyLoggedEmailProvider(EmailConfiguration configuration) {
    super(configuration);
  }

  @Override
  public OutboundEmail send(OutboundEmail email) throws EmailFailedException {
    log.info("Sending email to: " + email.getToAddresses());
    log.info("From: " + email.getFromAddress());
    log.info(email.getContent());
    log.info("Done.");
    email.setSent(true);
    return email;
  }

}
