package com.anglesharp.app.util;

import java.util.UUID;

public class UUIDUtil {
  
  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
  
  public static String code() {
    return uuid() + "-" + uuid();
  }
}
