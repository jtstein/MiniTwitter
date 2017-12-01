/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Hashtag;
import business.Twit;
import business.User;
import dataaccess.HashtagDB;
import dataaccess.TwitDB;
import dataaccess.TwitHashtagDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jordan Stein
 */
@WebServlet(name = "twitServlet", urlPatterns = {"/twit"})
public class twitServlet extends HttpServlet {

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
                
        Twit twit = new Twit("","","","",-1);
        User user = (User)session.getAttribute("user");
        
        // get current action
        String action = "";
        action = request.getParameter("action");

        // perform action and set URL to appropriate page
        if (action.equals("twit")) {
            
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String text = request.getParameter("text");
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = (String)dtf.format(now);
            
            twit.setFullName(fullName);
            twit.setEmailAddress(email);
            twit.setText(text);
            twit.setDate(date);
            twit.setUserID(UserDB.getUserID(user.getFullName()));
            
            int mentionedUserID = -1;
            // check if a user was mentioned.
            if (text != null){
                String[] splt = text.split(" ");
                String firstName = "";
                String lastName = "";

                // look for @ tag, get mentioned userId
                for(int i=0; i < splt.length; i++){
                    if (splt[i].charAt(0) == '@'){
                        // get the first name
                        firstName = splt[i].substring(1, splt[i].length());
                        if (i < splt.length-1){
                            lastName = splt[i+1];
                        }
                        mentionedUserID = UserDB.getUserID(firstName + " " + lastName);
                        break;
                    }
                }
            }
            twit.setMentionedUserID(mentionedUserID);

            String numTwits = Integer.toString(Integer.parseInt(user.getNumTwits())+1);
            user.setNumTwits(numTwits);
            
            
            
            if (email == null || email.isEmpty() || text == null ||
                text.isEmpty() || date == null ||  date.isEmpty()) { 
                message = "Twit validation failed.";
                url = "/home.jsp";
            }
            else{ // user has passed validation
                url = "/home.jsp"; // redirect user to home if signup succeeds
                // update user to increment numTwits
                UserDB.update(user);
                // insert new twit into twitdb
                TwitDB.insert(twit);
                
                // handle hashtags if there are any
                String[] splt = twit.getText().split(" ");
                List<String> hashtagsText = new ArrayList<String>();
                // look for # tag, handle hashtag
                for(int i=0; i < splt.length; i++){
                    if(splt[i].charAt(0) == '#'){
                        hashtagsText.add(splt[i]);
                    }
                }
               // if there are any hashtags in the twit
               if (hashtagsText.size() > 0){
                    for (int i=0; i < hashtagsText.size(); i++){
                        Hashtag hashtag = HashtagDB.search(hashtagsText.get(i));
                        // check if the hashtag already exists in the hash tag table
                        if (hashtag != null){
                            // increment the count in the table if the hashtag is in the table.
                            hashtag.incHashtagCount();
                            HashtagDB.updateHashtagCount(hashtag);
                        }
                        else{ // otherwise, insert the new hashtag into the table.
                            hashtag = new Hashtag();
                            hashtag.setHashtagText(hashtagsText.get(i));
                            hashtag.setHashtagCount(1);
                            HashtagDB.insert(hashtag);
                        }

                        // insert hashtag into tweetHashtag table
                        TwitHashtagDB.insert(TwitDB.getTwitID(twit), HashtagDB.getHashtagID(hashtag));
                    }
               }
            }
            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }
        if (action.equals("delete")){
            
            // get twitID to delete
            String twitID = request.getParameter("delTwitID");
            
            // if user refreshes page, delete might attempt twice
            if (TwitDB.hasTwit(twitID)){
                
                // get the twit
                Twit twitToDel = TwitDB.getTwitByID(twitID);
                // check if twit has hashtag
                String[] splt = twitToDel.getText().split(" ");
                List<String> hashtagsText = new ArrayList<String>();
                // look for # tag, handle hashtag
                for(int i=0; i < splt.length; i++){
                     if (splt[i] != null && splt[i].length() > 0){
                        if(splt[i].charAt(0) == '#'){
                            hashtagsText.add(splt[i]);
                        }
                    }
                }
                // if there are any hashtags in the twit
                if (hashtagsText.size() > 0){
                     for (int i=0; i < hashtagsText.size(); i++){
                         Hashtag hashtag = HashtagDB.search(hashtagsText.get(i));
                         // check if the hashtag already exists in the hash tag table
                         if (hashtag != null){
                             
                             // decrement the count in the table if the hashtag is in the table.
                             hashtag.decHashtagCount();
                             // if the count is zero, remove the hashtag from the HashtagDB
                             if (hashtag.getHashtagCount() == 0){
                                 HashtagDB.delete(hashtag);
                             }else{ // otherwise, update the HashtagDB for decremented count
                                HashtagDB.updateHashtagCount(hashtag);
                             }
                         }
                         System.out.println("deleting hashtag with twitid " + twitID);
                         // delete twit with hashtag from tweetHashtag table
                         TwitHashtagDB.delete(twitID);
                     }
                }
                
                TwitDB.delete(twitID);
                // decrement the number of twits
                user.setNumTwits(Integer.toString(Integer.parseInt(user.getNumTwits())-1));
                // update user for decremented twits
                UserDB.update(user);
                
            }
            
            url = "/home.jsp";
        }
        
        if (url.equals("/home.jsp")){
            List<User> allUsers = UserDB.selectAll();
            List<Twit> allTwits = TwitDB.search(user);
            List<Twit> entireTwitDB = TwitDB.getAll();
            List<Hashtag> trendyHashtags = HashtagDB.getTrendyHashtags();

            session.setAttribute("allUsers", allUsers);
            session.setAttribute("allTwits", allTwits);
            session.setAttribute("entireTwitDB", entireTwitDB);
            session.setAttribute("trendyHashtags", trendyHashtags);
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
        return "This servlet inserts new twits into the TwitDB.";
    }// </editor-fold>

}
