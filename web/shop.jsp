
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body  bgcolor="#ffffff">
        <h1>login success!</h1>
        Welcome：
        <% String userName  = session.getAttribute("currentUser").toString(); %>
        <%= userName %>
        <% System.out.println(session.getAttribute("currentUser")); %>      
        <p></p>
        <img src="images/welcome.jpg" alt="welcome"/>
    </body>
</html>
