# twotter
Mini-Twitter Clone. Java Web Application.

Included is the Netbeans project.

Requires: Apache Tomcat Server + MySQL server / MySQLWorkbench. 

For full functionality, add your MySQL username and password to MyTwitter/web/META-INF/context.xml and replace your gmail username and password on line 70 in MyTwitter/src/java/dataaccess/MailUtilGmail.java

Features:
* Sign In, remember me
* Sign Up, (store user data into database)
* Forgot password, (generates, hashes new password and emails it to associated user)
* Home Page, (shows all tweets by users you follow, your tweets, and tweets you're mentioned in)
* Twot, (stores tweets in database)
* Follow/Unfollow, (updates follow database)
* Mentions (@Jordan Stein, stores mentions in tweets db)
* Hashtags (#coffee, stores into hashtag db)
* Trending Hashtags
* Hashtag pages, (shows all tweets including the hashtags)
* Edit Profile, (updates user db whenever a user makes a change)
* Notifications, (displays all new tweets the user is mentioned in and new followers since their last login)
* All passwords stored are salted and hashed for internal security.

## Login Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/loginPage.png)

## Home Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/homePage.png)

## Notifications Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/notificationsPage.png)

## Hashtag Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/hashtagPage.png)

## Sign Up Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/signupPage.png)

## Edit Profile Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/editprofilePage.png)

## Forgot Password Page
![Settings Window](https://raw.github.com/jtstein/twotter/master/forgotPasswordPage.png)
