<%-- 
    Document   : hashtag.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : Jordan Stein
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>twotter: #${param.hashtag}</title>
        <link rel="shortcut icon" type="image/png" href="images/twotterbird.png" />
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel = "stylesheet" href="styles/main.css?v=1" type="text/css" />
        <script src="javascript/main.js" type="text/javascript"></script>
        <img class = "footerImg" src="images/twotterside.png"/>
    </head>
    <jsp:include page="header.jsp" />
    <!--<div id="homePage">-->
    <body>
        <c:if test="${!sessionScope.loggedIn}">
            <c:redirect url="/login.jsp"></c:redirect>
        </c:if>
        
        <div id="userInfo">
            <h3>${sessionScope.user.fullName}</h3>
            <h3>${sessionScope.user.email}</h3>
            <h3>Number of twits: <span style="color:black">${sessionScope.user.numTwits}</span></h3>
        </div>
                
        <div id="whoToFollow">
            <h1>Who To Follow</h1>
            
            <c:forEach var="user" items="${sessionScope.allUsers}">
                
                <c:set var="isFollowing" scope="page" value="false"/>

            <c:if test="${sessionScope.user.email != user.getEmail()}">
                <h3 style="display:inline;">${user.getFullName()}</h3>
                <c:if test="${empty sessionScope.following}">
                    <form action = "follow" method = "post">
                    <input type= "hidden" name = "followID" value= "${user.getUserID()}"/>
                    <input type="hidden" name="action" value="follow"/>
                    <input type="hidden" name="prevURL" value="/hashtag.jsp"/>
                    <input type="hidden" name="prevHashtag" value="${param.hashtag}"/>

                    <input class = "delButton" style="display:inline; float:right;width: 55px;" type="submit" size="2000" value="Follow">   
                    </form>
                </c:if>
                
                <c:if test="${not empty sessionScope.following}">
                <c:forEach var="following" items = "${sessionScope.following}">
                <form action = "follow" method = "post">
                    <input type= "hidden" name = "followID" value= "${user.getUserID()}"/>
                    <input type="hidden" name="prevURL" value="/hashtag.jsp"/>
                    <input type="hidden" name="prevHashtag" value="${param.hashtag}"/>

                    <td>
                        <c:choose>
                        <c:when test="${following.getEmail() == user.getEmail()}">
                            <input type="hidden" name="action" value="unfollow"/>
                            <input type="hidden" name="prevURL" value="/hashtag.jsp"/>
                            <input class = "delButton" style="display:inline; float:right;width: 65px;" type="submit" size="2000" value="Unfollow">
                            
                            <c:set var="isFollowing" scope="page" value = "true"/>
                        </c:when> 
                        </c:choose>
                            </c:forEach>
                            
                        <c:if test="${isFollowing == false}">
                            <input type="hidden" name="action" value="follow"/>
                            <input class = "delButton" style="display:inline; float:right;width: 55px;" type="submit" size="2000" value="Follow">   
                        </c:if>
                    </td>
                </form>
                </c:if>
                ${user.getEmail()}<br>
            </c:if>
            </c:forEach>
        </div>
        
        <div id="trends">
            <h1>Trends</h1>
            <c:forEach var="trendy" items="${sessionScope.trendyHashtags}">
                <tr>
                    <td>${trendy.getHashtagText()} : ${trendy.getHashtagCount()}</td>
                </tr>
                <br>
            </c:forEach>
        </div>
        
        <span style="color: red">${message}</span>
        
        <div id="allTwits">
            <br>
            <h1>#<%= request.getParameter("hashtag") %></h1>
            
            <c:set var="hashtag" scope="request" value = "#${param.hashtag}"/>
                                                    
            <c:forEach var = "hashtagTwit" items="${sessionScope.entireTwitDB}">
            <c:if test="${fn:contains(hashtagTwit.getText(),hashtag)}">
                <table>
                    <tr><td style="font-size:16px; float:left;">${hashtagTwit.getDate()}</td></tr>
                    <td style="font-size:16px; text-align:left;">${hashtagTwit.getFullName()}: ${hashtagTwit.getText()}</td>
                </table>
                <br>
            </c:if>
            </c:forEach>
        </div>
    </body>
    <jsp:include page="footer.jsp" />
</html>
