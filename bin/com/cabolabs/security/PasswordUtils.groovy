package com.cabolabs.security

import org.mindrot.jbcrypt.BCrypt

class PasswordUtils {

   // https://docs.spring.io/spring-security/site/docs/2.0.7.RELEASE/apidocs/org/springframework/security/providers/encoding/PasswordEncoder.html
   public static String encodePassword(String rawPass)
   {
      return BCrypt.hashpw(rawPass, BCrypt.gensalt())
   }

   public static boolean isPasswordValid(String encPass, String rawPass)
   {
      return BCrypt.checkpw(rawPass, encPass)
   }
}
