package com.cabolabs.security

import org.grails.testing.GrailsUnitTest
import spock.lang.Specification

// https://github.com/spring-projects/spring-framework/blob/master/spring-web/src/test/java/org/springframework/mock/web/test/MockHttpSession.java
// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/mock/web/MockHttpSession.html
import org.springframework.mock.web.MockHttpSession


class SessionManagerSpec extends Specification implements GrailsUnitTest {

   MockHttpSession session

   def setup()
   {
      session = new MockHttpSession()

      UserMock.createUser('a', 'a')
      UserMock.createUser('b', 'b')
      UserMock.createUser('c', 'c')

      //println UserMock.database
   }

   def cleanup()
   {
      UserMock.clean()
   }

   void "basic authentication"()
   {
      when:
         def authp = new ClosureAuthProvider()

      then:"authentication is executed"
         def run_isauth = authp.authenticate([user:iuser, pass:ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return false
            }
            PasswordUtils.isPasswordValid(u.password, pass) // returns boolean
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

   void "session creation"()
   {
      when:
         def man = SessionManager.getInstance()
         def authp = new ClosureAuthProvider()

      then:
         def run_isauth = authp.authenticate([user: iuser, pass: ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return false
            }
            PasswordUtils.isPasswordValid(u.password, pass) // returns boolean
         }
         if (run_isauth)
         {
            //println session.id
            def sess = new Session(jsessid: session.id.toString(),
                                   userId: iuser,
                                   authenticated: true,
                                   payload: [
                                     mydata: "abc",
                                     otherdata: 123
                                   ])
            man.addSession(sess)
         }

      expect:
         run_isauth == expected_isauth
         if (run_isauth)
         {
            man.hasSession(session.id.toString())
            man.getSession(session.id.toString()) != null
            man.getSession(session.id.toString()).jsessid == session.id.toString()
            man.getSession(session.id.toString()).userId == iuser
         }
         else
         {
            !man.hasSession(session.id.toString())
            man.getSession(session.id.toString()) == null
         }

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
