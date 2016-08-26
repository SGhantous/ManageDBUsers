<%-- 
    Document   : admin
    Created on : Nov 12, 2015, 10:30:31 AM
    Author     : 339250
--%>

<%@page import="adminhelper.AdminHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assignment 3 Main Page</title>
    </head>
    <body>
        <h1>Admin Page</h1>
        <%
            String username = (String)session.getAttribute("username");
            out.println("Welcome, " + username );
        %>
        <br>
        <a href="Validate?logout=true">Logout</a>
    </body>
    <br>
    <h2>Users</h2>
    <br>
    <%
        AdminHelper helper = new AdminHelper();
        out.println(helper.displayTable());
    %>
</html>
