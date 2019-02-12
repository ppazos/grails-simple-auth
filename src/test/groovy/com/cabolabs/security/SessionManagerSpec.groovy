package com.cabolabs.security

import org.grails.testing.GrailsUnitTest
import spock.lang.Specification

class SessionManagerSpec extends Specification implements GrailsUnitTest {

/*
   static class Database {

      static List dataset = [] // database mock

      static add(u)
      {
         dataset << u
      }

      static find(Closure compare)
      {
         dataset.find(compare)
      }

      static clean()
      {
         dataset = []
      }
   }
   */

   def setup()
   {
      UserMock.createUser('a', 'a')
      UserMock.createUser('b', 'b')
      UserMock.createUser('c', 'c')

      println UserMock.database
   }

   def cleanup()
   {
      UserMock.clean()
   }

   void "basic login"()
   {
      when:
         def man = SessionManager.getInstance()
         def authp = new UserPassProvider()

      then:"authentication is executed"
         def run_isauth = authp.authenticate([user:iuser, pass:ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return false
            }
            PasswordUtils.isPasswordValid(u.password, pass)
         }

      expect:
         run_isauth == expected_isauth

      where:
         iuser | ipass | expected_isauth
         'a'   | 'a'   | true
         'b'   | 'b'   | true
         'c'   | 'c'   | true
         'a'   | 'x'   | false
         'x'   | 'x'   | false
   }

   /*
   void "test something"() {
      expect:"fix me"
         true == false
   }
   */
}
