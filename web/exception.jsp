<%-- 
    Document   : exception
    Created on : 23-May-2014, 16:20:27
    Author     : Steve Hoult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>pizzashop::exception</title>
    </head>
    <body>
        <h1>Cannot order this pizza now due to a lack of ingredients, will be back on shop soon!</h1>
        <form action="shop.jsp" method=post>
            <tr bgcolor="#FFFFRF" >
            <input type="submit" value="Back" name="submit"/>
        </form>

    </body>
</html>
