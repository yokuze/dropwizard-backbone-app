package com.anglesharp.app.core;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @Table(name = "outbound_email")
public class OutboundEmail {
  
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String toAddresses;
  private String fromAddress;
  private String bcc;
  
  private String subject;
  
  @Lob
  private String content;
  
  private int failures;
  private boolean sent;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date sendAfter;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastTry;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToAddresses() {
    return toAddresses;
  }

  public void setToAddresses(String toAddresses) {
    this.toAddresses = toAddresses;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public String getBcc() {
    return bcc;
  }

  public void setBcc(String bcc) {
    this.bcc = bcc;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getFailures() {
    return failures;
  }

  public void setFailures(int failures) {
    this.failures = failures;
  }

  public boolean isSent() {
    return sent;
  }

  public void setSent(boolean sent) {
    this.sent = sent;
  }

  public Date getSendAfter() {
    return sendAfter;
  }

  public void setSendAfter(Date sendAfter) {
    this.sendAfter = sendAfter;
  }

  public Date getLastTry() {
    return lastTry;
  }

  public void setLastTry(Date lastTry) {
    this.lastTry = lastTry;
  }

}