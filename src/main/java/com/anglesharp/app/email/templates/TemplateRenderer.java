package com.anglesharp.app.email.templates;

import java.util.Map;

public interface TemplateRenderer {
  public String render(String name, Map<String, Object> args);
}
