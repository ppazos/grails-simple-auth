package com.cabolabs.security

class Session {

   String uid = java.util.UUID.randomUUID() as String
   String userId
   Date createdOn

   Map payload // extra data
}
