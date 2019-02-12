package com.cabolabs.security

class SessionManager {

   private static SessionManager instance = new SessionManager()
   private static sessions = [:]

   private SessionManager()
   {

   }

   public static SessionManager getInstance()
   {
      instance
   }

   public add(Session sess)
   {

   }

   public kill(Session sess)
   {

   }

   public kill(String sessUid)
   {
      
   }
}
