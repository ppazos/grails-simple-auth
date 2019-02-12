package com.cabolabs.security

interface AuthenticationProvider {

   public boolean authenticate(Map authData)

   public deauthenticate(Map matchData)
}
