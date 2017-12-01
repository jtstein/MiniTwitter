<%-- 
    Document   : header.jsp
    Created on : Sep 24, 2015, 6:47:09 PM
    Author     : Jordan Stein
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "stylesheet" href="styles/main.css?v=1" type="text/css" />
        <link rel = "stylesheet" href="styles/header.css?v=1" type="text/css" />
    </head>
    <body>
        <header>
            <c:if test="${sessionScope.loggedIn}">
                <ul class = "headerNav">
                    <li><a href="home.jsp">Home</a></li>
                    <li><a href="notifications.jsp">Notifications</a></li>                    
                    <li class="dropdown">
                      <a href="javascript:void(0)" class="dropbtn">Profile</a>
                      <div class="dropdown-content">
                        <a href="editprofile.jsp">Edit Profile</a>
                        <form action="membership" method="post" id="signoutForm">
                            <input type="hidden" name="action" value="logout">
                        </form>
                        <button type="submit" form="signoutForm" class = "signoutLink">Sign Out</button>
                      </div>
                    </li>
                    </input>
                    </form>
                </ul>
            </c:if>
        </header>
    </body>
</html>
