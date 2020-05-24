package com.cabolabs.security

class ClosureAuthProvider {

   public AuthResult authenticate(Map authData, Closure c)
   {
      authData['provider'] = this
      c.delegate = authData
      c.call()
   }

   // These will be used by the auth closure to factory results
   AuthResult authOk(String message)
   {
      new AuthResult(
         result: true,
         message: message
      )
   }

   AuthResult authError(String message)
   {
      new AuthResult(
         result: false,
         message: message
      )
   }
}
