package com.anglesharp.app.email.templates;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;


public class TemplateUtil {
  
  private static String TEMPLATES_FOLDER = "emails/";
  
  public static String load(String name) {
    
    if(name == null)
      return "";
    
    if(name.startsWith("/"))
      name = name.substring(1);
    
    InputStream s = TemplateUtil.class.getClassLoader().getResourceAsStream(TEMPLATES_FOLDER + name);
    
    return read(s);
  }

  private static String read(InputStream s) {
    try {
      return IOUtils.toString(s);
    } 
    catch (IOException e) {
      return null;
    }
  }
}
