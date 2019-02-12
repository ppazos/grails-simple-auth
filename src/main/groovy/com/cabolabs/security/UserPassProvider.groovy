package com.cabolabs.security

class UserPassProvider {

   public boolean authenticate(Map authData, Closure c)
   {
      c.delegate = authData
      c.call()
   }

   public deauthenticate(Map matchData)
   {

   }
}
