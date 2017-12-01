/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Follow;
import business.Twit;
import business.User;
import dataaccess.FollowDB;
import dataaccess.TwitDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
@WebServlet(name = "followServlet", urlPatterns = {"/follow"})
public class followServlet extends HttpServlet {

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
        doPost(request, response);
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
        
            // get url of page this servlet was called from
            String url = request.getParameter("prevURL");
            if (url == null){
                url = "/home.jsp";
            }
        
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("user");

            // get current action
            String action = "";
            action = request.getParameter("action");
            
            if (action.equals("follow")){
                
                int userID = UserDB.getUserID(user.getFullName());
                int followedUserID = Integer.parseInt(request.getParameter("followID"));
                
                // get current date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = (String)dtf.format(now);
                // create new follow
                Follow follow = new Follow(userID, followedUserID, date);
                // insert follow into database
                FollowDB.insert(follow);
                
            }
            else if (action.equals("unfollow")){
                int userID = user.getUserID();
                int unfollowID = Integer.parseInt(request.getParameter("followID"));
                
                // remove follow from FollowDB
                FollowDB.delete(userID, unfollowID);
            }
            
            // get all user ids who you follow
            List<User> following = FollowDB.getFollowerIDs(user.getUserID(), true);
            // get all user ids of who follow you
            List<User> followers = FollowDB.getFollowerIDs(user.getUserID(), false);
            // get twits of everyone you follow
            List<Twit> followingTwits = TwitDB.getFollowingTwits(following);

            session.setAttribute("following", following);
            session.setAttribute("followers", followers);
            session.setAttribute("followingTwits", followingTwits);
           

            // if we were on the hashtag page, add hashtag to the query
            if (url.equals("/hashtag.jsp")){
                url += "?hashtag="+request.getParameter("prevHashtag");
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
        return "Short description";
    }// </editor-fold>

}
