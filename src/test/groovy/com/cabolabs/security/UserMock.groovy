package com.cabolabs.security

class UserMock {

   static database = []

   String username
   String password

   static UserMock findByUsername(String username)
   {
      //return dataset.find{ it.username == username }
      database.find { it.username == username }
   }

   static createUser(String username, String password)
   {
      database << new UserMock(username:username, password:PasswordUtils.encodePassword(password))
      // username:username, password:PasswordUtils.encodePassword(password)
   }

   static clean()
   {
      database = []
   }
}
