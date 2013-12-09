package com.anglesharp.app.email;

import com.anglesharp.app.AppConfiguration.EmailConfiguration;

public class EmailProviderFactory {
  
  public enum Type { 
    
    DEFAULT, INTERNAL;
    
    public static Type get(String type) {
      if(type == null)
        return DEFAULT;
      
      for(Type t : values())
        if(t.toString().equalsIgnoreCase(type))
          return t;
      
      return DEFAULT;
    }
  };
  
  public static EmailProvider create(EmailConfiguration email) { 
    Type type = Type.get(email.getProvider());
    
    switch(type) {
      case DEFAULT:
        return new HtmlEmailProvider(email);
      case INTERNAL:
        return new InternallyLoggedEmailProvider(email);
      default:
        return new HtmlEmailProvider(email);
    }
  }
}