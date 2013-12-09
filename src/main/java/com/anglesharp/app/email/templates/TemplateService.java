package com.anglesharp.app.email.templates;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anglesharp.app.AppConfiguration;

public class TemplateService {
  
  private Logger log = LoggerFactory.getLogger(TemplateService.class);
  
  private final AppConfiguration config;
  
  public TemplateService(AppConfiguration config) {
    this.config = config;
  }
  
  public String render(String filename, Map<String, Object> renderArgs) {
    return TemplateRendererFactory.create().render(filename, renderArgs);
  }
}