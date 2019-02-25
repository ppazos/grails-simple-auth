# grails-simple-auth
Plugin for simple authentication and authorization

## Usage

Create your login controller and view as you want, based on your app needs.

## Authentication

Example of a username and password authentication process. Just change the "USERNAME" and "PASSWORD" strings with the values that you take from a user's input on the login view, and that's it.

```groovy
import com.cabolabs.security.*
...

def man = SessionManager.getInstance()
def authp = new ClosureAuthProvider()

// Define the authentication process as you need, using a closure and the params
// you need. This is an example of a user+pass authentication. Always return a
// boolean from the auth closure!

def isauth = authp.authenticate([user: "USERNAME", pass: "PASSWORD"]) { ->
   def u = UserMock.findByUsername(user)
   if (!u)
   {
      return false
   }
   PasswordUtils.isPasswordValid(u.password, pass) // returns boolean
}

// If the authentication was successful, create the Session object and add it
// to the manager, that is a singleton container for your user sessions.
if (isauth)
{
   def sess = new Session(jsessid: session.id.toString(),
                          userId: "USERNAME",
                          authenticated: true,
                          payload: [
                            mydata: "abc",
                            otherdata: 123
                          ])
   man.addSession(sess)
}

// Then you can easily verify if a user is logged in or not.
if (man.hasSession(session.id.toString()))
{
   // is logged in
}
else
{
   // is not logged in
}
```

## Using the taglib on views to display private content

This simple taglib checks if the user has a session and displays the content within the taglib. If the current user is not logged in, the content is not displayed. Just add this to the secured content on your views.

```xml
<sa:isLoggedIn>Important content only for logged in users</sa:isLoggedIn>
```
