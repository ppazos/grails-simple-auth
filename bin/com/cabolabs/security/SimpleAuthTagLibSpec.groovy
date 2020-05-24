package com.cabolabs.security

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class SimpleAuthTagLibSpec extends Specification implements TagLibUnitTest<SimpleAuthTagLib> {

   def setup() {
   }

   def cleanup() {
   }

   void "test with session"() {
      when:
         // login mock
         def man = SessionManager.getInstance()
         def sess = new Session(jsessid: session.id.toString(),
                                userId: 'a',
                                authenticated: true,
                                payload: [
                                  mydata: "abc",
                                  otherdata: 123
                                ])
         man.addSession(sess)

         def body = "hidden content"
         def output = applyTemplate('<sa:isLoggedIn>hidden content</sa:isLoggedIn>')

      then:"content is displayed"
         output == body
   }

   void "test without session"() {
      when:
         def body = ""
         def output = applyTemplate('<sa:isLoggedIn>hidden content</sa:isLoggedIn>')

      then:"content is not displayed"
         output == body
   }
}
