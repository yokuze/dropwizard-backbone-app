package com.anglesharp.app.email;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.core.OutboundEmail;
import com.anglesharp.app.db.OutboundEmailDAO;
import com.anglesharp.app.email.EmailProvider.EmailFailedException;
import com.anglesharp.app.email.templates.TemplateService;
import com.google.common.collect.Maps;

public class EmailService {
  
  private Logger log = LoggerFactory.getLogger(EmailService.class);
  
  private final AppConfiguration config;
  private final OutboundEmailDAO emails;
  
  public EmailService(AppConfiguration config, OutboundEmailDAO emails) {
    this.config = config;
    this.emails = emails;
  }
  
  public void send(OutboundEmail email) {
    EmailProvider provider = EmailProviderFactory.create(config.getEmail());
    
    try {
      provider.send(email);
    } 
    catch (EmailFailedException e) {
      log.error("Sending email failed for " + email.getToAddresses() + ": ", e);
      
      email.setFailures(email.getFailures() + 1);
      email.setLastTry(new Date());
      emails.create(email);
    }
    
    if(email.getId() != null)
      emails.save(email);
  }
  
  public void send(String content, String to, String subject) {
    OutboundEmail email = createEmail(content, to, subject);
    send(email);
  }
  
  public void send(String filename, Map<String, Object> renderArgs, String to, String subject) {
    TemplateService templateService = new TemplateService(config);
    
    String content = templateService.render(filename, renderArgs);
    OutboundEmail email = createEmail(content, to, subject);
    
    send(email);
  }
  
  private OutboundEmail createEmail(String content, String to, String subject) {
    OutboundEmail email = new OutboundEmail();
    email.setContent(content);
    email.setToAddresses(to);
    email.setSubject(subject);
    email.setFromAddress(config.getEmail().getFrom());
    return email;
  }
  
  public static class RenderArgs {
    private Map<String, Object> args = Maps.newHashMap();
    
    private RenderArgs() {
      
    }
    
    public RenderArgs put(String s, Object o) {
      args.put(s, o);
      return this;
    }
    
    public Map<String, Object> get() {
      return args;
    }
    
    public static RenderArgs create() {
      return new RenderArgs();
    }
  }
}