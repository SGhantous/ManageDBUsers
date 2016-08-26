<%-- 
    Document   : main
    Created on : Nov 12, 2015, 10:30:23 AM
    Author     : 339250
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assignment 3 Main Page</title>
    </head>
    <body>
        <h1>Main Page</h1>
        <%
            String username = (String)session.getAttribute("username");
            out.println("Welcome, " + username );
        %>
        <br>
        <a href="Validate?logout=true">Logout</a>
    </body>
</html>
