/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Hashtag;
import business.Twit;
import business.User;
import dataaccess.FollowDB;
import dataaccess.HashtagDB;
import dataaccess.TwitDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.PasswordUtil;

/**
 *
 * @author Jordan Stein
 */
@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class membershipServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String fileName = "database.txt";
        ServletContext sc = this.getServletContext();
        String pathName = sc.getRealPath(request.getContextPath());
        pathName = pathName.substring(0, pathName.length() - 20) + "/web/" + fileName;
        
        String url = "";
        String message = "";
        
        boolean loggedIn = false;
        
        User user = new User("","","","","","","","","");
        
        // get current action
        String action = "";
        action = request.getParameter("action");

        // perform action and set URL to appropriate page
        if (action != null && action.equals("signup")) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String DoB = request.getParameter("DoB");
            String securityQuestion = request.getParameter("securityQuestion");
            String securityAnswer = request.getParameter("securityAnswer");
            
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(password);
            user.setDoB(DoB);
            user.setSecurityQuestion(securityQuestion);
            user.setSecurityAnswer(securityAnswer);
            user.setNumTwits("0");
            
            // hash and salt password
            String salt = "";
            String saltedAndHashedPassword = "";
            try {
                salt = PasswordUtil.getSalt();
                saltedAndHashedPassword = PasswordUtil.hashAndSaltPassword(password, salt);                    

            } catch (NoSuchAlgorithmException ex) {
                saltedAndHashedPassword = ex.getMessage();
            }
            
            if (salt != ""){
                user.setSalt(salt);
            }
            if (saltedAndHashedPassword != ""){
                user.setPassword(saltedAndHashedPassword);
            }
            
            // get current date/time for lastLogin user field
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = (String)dtf.format(now);  
            // update user with lastLogin date
            user.setLastLogin(date);  
        
            
            if (fullName == null || fullName.isEmpty() || email == null ||
                email.isEmpty() || password == null ||  password.isEmpty() || 
                DoB == null || DoB.isEmpty() || securityQuestion == null ||
                securityQuestion.isEmpty() ||  securityAnswer == null || 
                securityAnswer.isEmpty()) {
                
                message = "Form validation failed.";
                url = "/signup.jsp";
                loggedIn = false;
            }
            else if(UserDB.emailExists(email)){
                message = "An account with that email already exists.<br>";
                url = "/signup.jsp";
                loggedIn = false;
            }
            else{ // user has passed validation
                url = "/home.jsp"; // redirect user to home if signup succeeds
                loggedIn = true;
                UserDB.insert(user);
            }
            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }
        else if(action.equals("editprofile")){
            user.setFullName(request.getParameter("fullName"));
            user.setEmail(request.getParameter("email"));
            String password = request.getParameter("password");
            // hash new password
            try {
                String salt = PasswordUtil.getSalt();
                user.setPassword(PasswordUtil.hashAndSaltPassword(password, salt));
                user.setSalt(salt);
                System.out.println("newpass is " + PasswordUtil.hashAndSaltPassword(password, salt));
            } catch (NoSuchAlgorithmException ex) {
                
            }
            
            user.setDoB(request.getParameter("DoB"));
            user.setSecurityQuestion(request.getParameter("securityQuestion"));
            user.setSecurityAnswer(request.getParameter("securityAnswer"));
            user.setNumTwits(request.getParameter("numTwits"));
            
            UserDB.update(user);
            session.setAttribute("user", user);

            url = "/home.jsp";
            loggedIn = true;
        }
        else if (action.equals("login")){
            
            String userEmail = request.getParameter("userEmail");
            String userPassword = request.getParameter("userPassword");
            
            user = UserDB.search(userEmail);
            
            // check if user already had previous salt since the update.
            boolean isSalted = true;

            if (user.getSalt().equals("NA")){
                isSalted = false;
            }
            // salt and hash password to check it against UserDB
            String salt = "";
            try {
                if(isSalted){ // get stored salt
                    salt = user.getSalt();
                    userPassword = PasswordUtil.hashAndSaltPassword(userPassword, salt);

                }else{ // generate new salt
                    salt = PasswordUtil.getSalt();
                }                

            } catch (NoSuchAlgorithmException ex) {
                userPassword = ex.getMessage();
            }
            
            if (user != null && userPassword.equals(user.getPassword())){
                // login successful
                request.setAttribute("user", user);
                request.setAttribute("message", message);
                
                if(!isSalted){
                    // set new salt
                    user.setSalt(salt);
                    try{
                    // hash and salt the current password
                    userPassword = PasswordUtil.hashAndSaltPassword(userPassword, salt);
                    } catch(NoSuchAlgorithmException ex){
                        userPassword = ex.getMessage();
                    }
                    // set the new hashed password
                    user.setPassword(userPassword);
                    // update UserDB for new password + salt
                    UserDB.update(user);
                }
            
                
                // check if user clicked on remember me checkbox
                if(request.getParameter("rememberMe") != null){
                    // add a cookie that stores the user's email to browser
                    Cookie c = new Cookie("emailTwotter", user.getEmail());
                    c.setMaxAge(60 * 60 * 24 * 365); // set age to 1 year
                    c.setPath("/");                  // allow entire app to access it
                    response.addCookie(c);
                }
                
                url = "/home.jsp";
                loggedIn = true;
            }
            else{
                // login failed
                message = "Invalid username/email or<br>password. Try again.<br><br>";
                request.setAttribute("message", message);
                url = "/login.jsp";
                loggedIn = false;
            }
        }
        else if (action.equals("logout")){            
            
            // get user from session
            user = (User)session.getAttribute("user");
            // get current date/time for lastLogin user field
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = (String)dtf.format(now); 
            
            // update user with lastLogin date
            user.setLastLogin(date);            
            UserDB.update(user);
            
            // remove all session attributes
            session.removeAttribute("user");
            session.removeAttribute("allUsers");
            session.removeAttribute("allTwits");
            session.removeAttribute("entireTwitDB");
            session.removeAttribute("loggedIn");
            session.removeAttribute("checkedCookies");
            session.removeAttribute("notificationTwits");
            session.removeAttribute("notificationFollowers");
            session.removeAttribute("following");
            session.removeAttribute("followers");
            session.removeAttribute("followingTwits");
            session.removeAttribute("trendyHashtags");
            
            // remove all cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            loggedIn = false;
            url = "/login.jsp";
        }
        
        List<User> allUsers = UserDB.selectAll();        
        session.setAttribute("loggedIn", loggedIn);
        if(loggedIn){
            if(user != null){
                session.setAttribute("user", user);
                if (allUsers != null){
                    session.setAttribute("allUsers",allUsers);
                }
            }
            if (url.equals("/home.jsp") || url.equals("/notifications.jsp") || url.startsWith("/hashtag.jsp")){
                List<Twit> entireTwitDB = TwitDB.getAll();
                List<Twit> allTwits = TwitDB.search(user);
                // store all new twits + follows in notifications list
                List<Twit> notificationTwits = TwitDB.getNotifications(user);
                List<User> notificationFollowers = FollowDB.getNotifications(user);
                
                // get all users who you follow
                List<User> following = FollowDB.getFollowerIDs(user.getUserID(), true);
                // get all users of who follow you
                List<User> followers = FollowDB.getFollowerIDs(user.getUserID(), false);
                
                List<Twit> followingTwits = TwitDB.getFollowingTwits(following);

                // get all trendy hashtags
                List<Hashtag> trendyHashtags = HashtagDB.getTrendyHashtags();
                
                session.setAttribute("entireTwitDB",entireTwitDB);
                session.setAttribute("allUsers", allUsers);
                session.setAttribute("allTwits", allTwits);
                session.setAttribute("notificationTwits", notificationTwits);
                session.setAttribute("notificationFollowers", notificationFollowers);
                session.setAttribute("following", following);
                session.setAttribute("followers", followers);
                session.setAttribute("followingTwits",followingTwits);
                session.setAttribute("trendyHashtags", trendyHashtags);
            }
        }
        
        getServletContext()
               .getRequestDispatcher(url)
                .forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This servlet creates a new member on sign up and stores them in the userDB.";
    }// </editor-fold>
}
