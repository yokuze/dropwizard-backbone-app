package com.anglesharp.app.email.templates;

import java.util.Map;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

public class MustacheTemplateRenderer implements TemplateRenderer {

  public String render(String name, Map<String, Object> args) {
    LoadedTemplate loadedTemplate = TemplateCache.get(name);
    
    if(loadedTemplate == null) {
      loadedTemplate = new LoadedTemplate();
      loadedTemplate.content = TemplateUtil.load(name);
      loadedTemplate.templateName = name;
      loadedTemplate.templateObject = Mustache.compiler().compile(loadedTemplate.content);
    }
    
    Template compiledTemplate = (Template)loadedTemplate.templateObject;
    
    return compiledTemplate.execute(args);
  }
  
}
