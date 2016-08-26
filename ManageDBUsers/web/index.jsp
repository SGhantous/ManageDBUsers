<%-- 
    Document   : index
    Created on : Nov 12, 2015, 10:30:15 AM
    Author     : 339250
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Steven Ghantous Assignment 3</title>
    </head>
    <body>
        <h1>User Management</h1>
        <form action="Validate" method="POST">
            Username: <input type="text" name="username">
            <br>
            Password: <input type="password" name="password">
            <br>
            <input type="submit" value="Login">
        </form>
        <br>
        <%
            String message = request.getParameter("message");
            
            if (message != null)
            {
                out.println(message);
            }
        %>
    </body>
</html>
