<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : Jordan Stein
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>twotter: home</title>
        <link rel="shortcut icon" type="image/png" href="images/twotterbird.png" />
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel = "stylesheet" href="styles/main.css?v=1" type="text/css" />
        <script src="javascript/main.js" type="text/javascript"></script>
        <img class = "footerImg" src="images/twotterside.png"/>
    </head>
    <jsp:include page="header.jsp" />
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
                    <input type="hidden" name="prevURL" value="/home.jsp"/>
                    <input class = "delButton" style="display:inline; float:right;width: 55px;" type="submit" size="2000" value="Follow">   
                    </form>
                </c:if>
                
                <c:if test="${not empty sessionScope.following}">
                <c:forEach var="following" items = "${sessionScope.following}">
                <form action = "follow" method = "post">
                    <input type= "hidden" name = "followID" value= "${user.getUserID()}"/>
                    <input type="hidden" name="prevURL" value="/home.jsp"/>

                    <td>
                        <c:choose>
                        <c:when test="${following.getEmail() == user.getEmail()}">
                            <input type="hidden" name="action" value="unfollow"/>
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

        <br><br>
        <form action = "twit" method = "post" class="twitForm">
            <input type="hidden" name="action" value="twit">
            <input type="hidden" name="fullName" value="${sessionScope.user.getFullName()}">
            <input type="hidden" name="email" value="${sessionScope.user.getEmail()}">
            <textarea name="text" rows="4" cols="50" maxlength = "140"></textarea>
            <label>&nbsp;</label>
                <input class="twitButton" type="submit" value="twot" class="margin_left">
        </form>
        
        <div id="allTwits">
            <div id="twits">
            <h1 style="display: inline; float:left;">Following Twots</h1>
            </div>
            <h1 style="display: inline-block;">Your Twots</h1>
            <h1 style="display: inline; float: right">Twots you're mentioned in</h1>
            
            <div id="twits">
            <table style="float: left; display: inline-table; width: 33%;">
                <c:forEach var = "followingTwit" items="${sessionScope.followingTwits}">
                    <tr>
                        <tr>
                            <td style="color:#e60000; font-size: 14px; float: left;">${followingTwit.getFullName()}</td>
                            <td style="color:#e60000; font-size: 10px;;">${followingTwit.getDate()}</td>                    
                        </tr>
                        <td style="color:black;">${followingTwit.getText()}</td>
                    </tr>
                </c:forEach>
            </table>
            </div>
                
            <div id="twits">
            <table style="float: left; display: inline-table; width: 33%;">
            <c:forEach var="twit" items="${sessionScope.allTwits}">
                <c:if test="${twit.getEmailAddress() == sessionScope.user.getEmail()}">
                <tr>
                    <td style="color:#e60000; font-size: 10px;">${twit.getDate()}</td>
                    <tr>
                    <form style="align:left;display:inline; white-space: pre;"action="twit" method = "post">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="delTwitID" value ="${twit.getTwitID()}"/>
                    <td>
                        <input class = "delButton" type="submit" size="2000"value="Del">
                    </td>
                    </form>
                
                    <td style="color:black;">${twit.getText()}</td></tr>
                </tr>
                </c:if>
            </c:forEach>
            </table>
            </div>

            <div id="twits">
            <table style="float: left; display: inline-table; width: 33%;">
            <c:forEach var="twit" items="${sessionScope.allTwits}">
                 <c:if test="${twit.getMentionedUserID() == sessionScope.user.getUserID() && twit.getMentionedUserID() != -1}">
                <tr>  
                    <tr>
                        <td style="color:#e60000; font-size: 14px; float: left;">${twit.getFullName()}</td>
                        <td style="color:#e60000; font-size: 10px;;">${twit.getDate()}</td>
                    </tr>
                    <td style="color:black;">${twit.getText()}</td>
                </tr>
                </c:if>
            </c:forEach>
            </table>
            </div>
        </div>
    </div>        
        </body>
    <jsp:include page="footer.jsp" />
</html>
