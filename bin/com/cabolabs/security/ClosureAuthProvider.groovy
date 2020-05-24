package com.cabolabs.security

class ClosureAuthProvider {

   public boolean authenticate(Map authData, Closure c)
   {
      c.delegate = authData
      c.call()
   }
}
