/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Twit;
import business.User;
import dataaccess.TwitDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.CookieUtil;

/**
 *
 * @author jordan
 */
@WebServlet(name = "redirectionServlet", urlPatterns = {"/redirection"})
public class redirectionServlet extends HttpServlet {
    
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
        
        ServletContext sc = this.getServletContext();
        HttpSession session = request.getSession();
        User user = null;
        boolean loggedIn = false;

        String url = "/login.jsp";
        Cookie[] cookies = request.getCookies();
        String email = CookieUtil.getCookieValue(cookies, "emailTwotter");

        // if cookie doesn't exist, go to Registration page
        if (email == null || email.equals("")) {
            loggedIn = false;
            url = "/login.jsp";
        }
        // if cookie exists, create User object and go to home page
        else {
            System.out.println("cookie exists...");
            user = UserDB.search(email);
            session.setAttribute("user", user);
            loggedIn = true;
            url = "/home.jsp";
        }

        request.setAttribute("checkedCookies",true);
                List<User> allUsers = UserDB.selectAll();  
                
        session.setAttribute("loggedIn", loggedIn);
        if(loggedIn){
            if(user != null){
                session.setAttribute("user", user);
                if (allUsers != null){
                    session.setAttribute("allUsers",allUsers);
                }
            }
            if (url.equals("/home.jsp")){
                List<Twit> allTwits = TwitDB.search(user);
                session.setAttribute("allUsers", allUsers);
                session.setAttribute("allTwits", allTwits);                
            }
        }
        // forward to the view
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
