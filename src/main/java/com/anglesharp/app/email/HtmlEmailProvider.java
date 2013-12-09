package com.anglesharp.app.email;

import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.anglesharp.app.AppConfiguration.EmailConfiguration;
import com.anglesharp.app.core.OutboundEmail;

public class HtmlEmailProvider extends EmailProvider {
  
  private static final int MAX_FAILURES = 5;
  
  public HtmlEmailProvider(EmailConfiguration configuration) {
    super(configuration);
  }

  @Override
  public OutboundEmail send(OutboundEmail email) throws EmailFailedException {
    
    if(!isReadyToSend(email)) {
      return email;
    }
    
    if(email.getFailures() >= MAX_FAILURES) {
      return email;
    }
    
    try {
      HtmlEmail htmlEmail = new HtmlEmail();
      htmlEmail.setHostName(configuration.getHost());
      htmlEmail.addTo(email.getToAddresses());
      htmlEmail.setFrom(email.getFromAddress());
      htmlEmail.setSubject(email.getSubject());
      
      // set the html message
      htmlEmail.setHtmlMsg(email.getContent());
  
      // set the alternative message
      //htmlEmail.setTextMsg("Your email client does not support HTML messages");
  
      // send the email
      htmlEmail.send();
      email.setSent(true);
      email.setFailures(0);
    } 
    catch (EmailException e) {
      throw new EmailFailedException(e);
    }
    
    return email;
  }
  
  private boolean isReadyToSend(OutboundEmail email) {
    return email.getSendAfter() == null || email.getSendAfter().compareTo(new Date()) <= 0;
  }
  
}