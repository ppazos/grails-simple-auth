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

   public Session getSession(String jsessid)
   {
      return sessions[jsessid]
   }

   public void addSession(Session sess)
   {
      if (sessions.find{ it.key == sess.jsessid })
      {
         throw new Exception("The user already has an active session "+ sess.jsessid)
      }
      sessions[sess.jsessid] = sess
   }

   public boolean hasSession(String jsessid)
   {
      return sessions.find{ it.key == jsessid } != null
   }

/*
   public boolean hasSession(String sessUid)
   {
      return sessions.find{ it.value.uid == sessUid } != null
   }
*/

   public void kill(String jsessid)
   {
      def entry = sessions.find{ it.key == jsessid }
      if (!entry)
      {
         throw new Exception("There is no active session with id "+ jsessid)
      }
      sessions.remove(jsessid)
   }
}
