<%-- 
    Document   : footer.jsp
    Created on : Sep 24, 2015, 6:47:16 PM
    Author     : xl
--%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel = "stylesheet" href="styles/main.css?v=1" type="text/css" />
    </head>
    <body>
    <footer>
    <%
         Date date = new Date();
         String dateList[] = date.toString().split(" ");
         String dayOfWeek = dateList[0];
         String month = dateList[1];
         String day = dateList[2];
         out.print( "<p inline style = \"padding : 0; margin-bottom : 0px; align = \"center\">" + dayOfWeek + " " + month + " " + day + "</p>");
    %>
        <p style = "margin-top: 0px;">© Stein Design 2017 | Design and website construction by Jordan Stein</p>

        
    </footer>
    </body>
</html>
