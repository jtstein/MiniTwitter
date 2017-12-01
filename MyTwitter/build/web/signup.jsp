<%-- 
    Document   : signup.jsp
    Created on : Sep 24, 2015, 6:44:47 PM
    Author     : Jordan Stein
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>twotter: sign up</title>
        <link rel="shortcut icon" type="image/png" href="images/twotterbird.png" />
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel = "stylesheet" href="styles/main.css?v=1" type="text/css" />
        <script src="javascript/main.js" type="text/javascript"></script>
        <img src="images/twotterlogo.png" class="centered"/>
    </head>
    <jsp:include page="header.jsp" />
    <body>
        <div id="signUpForm">
        <h1>Sign Up</h1>
        <form action = "membership" method = "post" name="signupform" onsubmit="return validateForm(event);">
            <span style="color: red">${message}</span>
            <input type="hidden" name="action" value="signup">

            <div id="errorMessage" class="notVisible"></div>
            <span id="tbFullName_error" class="notVisible">*</span>
            <label id ="lbFullName">Full Name</label><br>
            <input type="text" id = "tbFullName" name = "fullName" value ="${user.getFullName()}" class="textCSS" onclick = "test('clicked it')" required><br>
            
            <span id="tbEmail_error" class="notVisible">*</span>   
            <label id = "lbEmail">Email</label><br>
            <input type="email" id = "tbEmail" name = "email" value ="${user.getEmail()}" class="textCSS" required><br>
            
            <span id="tbPassword_error" class="notVisible">*</span>
            <label id="lbPassword">Password</label><br>
            <input type="password" id = "tbPassword" name = "password" value ="${user.getPassword()}"class="textCSS" required><br>
                
            <span id="tbConfirmPassword_error" class="notVisible">*</span>
            <label id="lbConfirmPassword">Confirm Password</label><br>
            <input type="password" id = "tbConfirmPassword" name = "confirmPassword" value ="${user.getPassword()}" class="textCSS" required><br>
            
            
            <fmt:parseDate pattern="yyyy-MM-dd" value="${user.getDoB()}" type = "date" var="date" />
            <span id="tbDoB_error" class="notVisible">*</span>
            <label id="lbDoB">Date of Birth</label><br>
            <input type="date" id = "tbDoB" name = "DoB" value ="${date}" class="textCSS" required><br>
            
            <br>
            <span id="tbSecurityQuestion_error" class="notVisible">*</span>
            <label id="lbSecurity">Security Question</label>
            <select id="securityQuestionCombo" name = "securityQuestion" value = "${user.getSecurityQuestion()}" onchange = 'createTextBox(this.value)'>
                <option value="choose">Choose one</option>
                <option value="pet">Your first pet</option>
                <option value="car">Your first car</option>
                <option value="school">Your first school</option>
            </select>
            <input class="notVisible" type="text" name= "securityAnswer" value = "${user.getSecurityAnswer()}" id="tbSecurityQuestion">

            <br><br>

            <label>&nbsp;</label>
                <input type="submit" value="Submit" class="margin_left">
        </form>
        </div> <!-- signUpForm div-->
    </body>
    
    <jsp:include page="footer.jsp" />
</html>