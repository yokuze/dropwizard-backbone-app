package com.anglesharp.app.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
  
  public static String hash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
  
  public static boolean check(String plaintextPassword, String hashedPassword) {
    return BCrypt.checkpw(plaintextPassword, hashedPassword);
  }
}
