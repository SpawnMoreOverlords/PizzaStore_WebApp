
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PizzaShop::login</title>
    </head>
    <body>
        <h1>Welcome to KAS Pizzashop! Please login</h1>
        <form name="form" action="loginCheck.jsp" method="post">
            Username：
            <input type="text" name="username" value="bob" />
            <br>
            Password：
            <input type="password" name="password" value="" />
            <p></p>
            <input type="submit" value="Login" name="submit" />
            
        </form>
        <form action="user.jsp" method=post>
            <input type="submit" value="Sign Up" name="submit"/>
        </form>

    </body>
</html>
