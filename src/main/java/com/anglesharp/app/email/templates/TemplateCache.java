package com.anglesharp.app.email.templates;

import java.util.Map;

import com.google.common.collect.Maps;

public class TemplateCache {
  private static Map<String, LoadedTemplate> cache = Maps.newConcurrentMap();
  
  public static void put(String templateName, LoadedTemplate template) {
    cache.put(templateName, template);
  }
  
  public static LoadedTemplate get(String templateName) {
    return cache.get(templateName);
  }
}
