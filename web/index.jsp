
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP login</title>
    </head>
    <body>
        <h1>Please login</h1>
        <form name="form" action="logincheck.jsp" method="post">
            Username：
            <input type="text" name="username" value="Steve" />
            <br>
            Password：
            <input type="password" name="password" value="" />
            <p></p>
            <input type="submit" value="Login" name="submit" />
            
        </form>
    </body>
</html>