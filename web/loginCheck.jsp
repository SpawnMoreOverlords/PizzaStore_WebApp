
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import = "Beans.*"%>
<jsp:useBean id = "user" class = "Beans.LoginBean" scope="page">     
    <jsp:setProperty  name="user" property="*"/>  
</jsp:useBean>
<%
user.setUsername(request.getParameter("username"));
user.setPassword(request.getParameter("password"));    
if(user.checklogin()){
    String username = request.getParameter("username");
    session.setAttribute("currentUser", username);
    ShoppingBean sb=new ShoppingBean();
    session.setAttribute("shoppingCart", sb);
    %>
    <jsp:forward page = "exception.jsp"></jsp:forward>
    <% } else {  %>
    <jsp:forward page="loginError.jsp"></jsp:forward>
<% }%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
