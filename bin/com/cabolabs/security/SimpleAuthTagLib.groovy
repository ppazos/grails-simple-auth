package com.cabolabs.security

class SimpleAuthTagLib {
   static namespace = 'sa'
   static defaultEncodeAs = [taglib:'html']
   //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

   def isLoggedIn = { attrs, body ->
      def man = SessionManager.getInstance()
      if (man.hasSession(session.id.toString()))
      {
         out << body()
      }
   }
}
