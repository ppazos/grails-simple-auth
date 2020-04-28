package com.cabolabs.security

class Session {

   //String uid = java.util.UUID.randomUUID() as String
   String jsessid
   String userId
   String userIdFieldName // name of the field in the client app used to identify the user like "username" or "email"
   Date createdOn = new Date()
   boolean authenticated

   Map payload // extra data
}
