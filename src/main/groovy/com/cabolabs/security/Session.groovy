package com.cabolabs.security

class Session {

   //String uid = java.util.UUID.randomUUID() as String
   String jsessid
   String userId
   Date createdOn = new Date()
   boolean authenticated

   Map payload // extra data
}
