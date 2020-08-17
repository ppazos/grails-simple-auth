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

   void "basic authentication"(String iuser, String ipass, boolean expected_isauth)
   {
      when:"authentication is executed"
         def authp = new ClosureAuthProvider()
         def result = authp.authenticate([user:iuser, pass:ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return authp.authError("no user")
            }
            if (PasswordUtils.isPasswordValid(u.password, pass))
            {
               return authp.authOk("welcome")
            }
            authp.authError("wrong credentials")
         }

      then:"check authenticated"
         result.result == expected_isauth

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
         def result = authp.authenticate([user: iuser, pass: ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return authp.authError("no user")
            }
            if (PasswordUtils.isPasswordValid(u.password, pass))
            {
               return authp.authOk("welcome")
            }
            authp.authError("wrong credentials")
         }
         if (result.result)
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
         result.result == expected_isauth
         if (result.result)
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

   void "get session by user id"()
   {
      when:
         def man = SessionManager.getInstance()
         def authp = new ClosureAuthProvider()

      then:
         def result = authp.authenticate([user: iuser, pass: ipass]) { ->
            def u = UserMock.findByUsername(user)
            if (!u)
            {
               return authp.authError("no user")
            }
            if (PasswordUtils.isPasswordValid(u.password, pass))
            {
               return authp.authOk("welcome")
            }
            authp.authError("wrong credentials")
         }
         if (result.result)
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
         result.result == expected_isauth
         if (result.result)
         {
            man.getSessionByUserId(iuser) != null
         }
         else
         {
            man.getSessionByUserId(iuser) == null
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
