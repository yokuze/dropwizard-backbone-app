package com.anglesharp.app.email.templates;

public class TemplateRendererFactory {
  
  public enum Type { DEFAULT, MUSTACHE }
  
  public static TemplateRenderer create() {
    return create(Type.DEFAULT);
  }
  
  public static TemplateRenderer create(Type type) {
    switch(type) {
      case DEFAULT:
        return new MustacheTemplateRenderer();
      case MUSTACHE:
        return new MustacheTemplateRenderer();
        
      default:
        return new MustacheTemplateRenderer();
    }
  }
}
