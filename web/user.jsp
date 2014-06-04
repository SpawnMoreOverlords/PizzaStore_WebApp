<%-- 
    Document   : user
    Created on : 2014-5-26, 22:33:24
    Author     : Kimi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="pizzashop" uri="/WEB-INF/tlds/pizzashop"%>

<html>
<head><title>PizzaShop::NEWUSER</title></head>
<body>
	<h1>Please create the profile for you. 
      </h1>
	${(passwordInvalid != null)?(passwordInvalid):null}
      <table border=0>
	<form action=pizzaServlet?action=usercreate method=post>
      <tr>
	<td>Username:</td>
      <td> <input type="text" name="user" value="${profile.user}" ></td>
      </tr>
      <tr>
	<td>New password:</td> 
      <td><input type="password" name="password" 
                      value = ${profile.password}></td>
      </tr>
      <tr>
	<td>Verify password:</td> 
      <td><input type="password" name="password2"
                        value = ${profile.password} ></td>
      </tr>
      <tr>
      <td>Name:</td>
      <td> <input type = "text" name = "name"
              value = "${profile.name}" ></td>
      </tr>
      <tr>
      <td>Street Address:</td> 
      <td><input type = "text" name = "street"
                       value = "${profile.street}"></td>
      </tr>
      <tr>
      <td>Zipcode:</td>
      <td><input type = "text" name = "zip"
                       value = "${profile.zip}"></td>
      </tr>
      <tr>
      <td>City:</td>
      <td> <input type = "text" name = "city"
              value = "${profile.city}"></td>
      </tr>
      <tr>
      <td>Country:</td>
      <td> <input type = "text" name = "country"
              value = "${profile.country}"></td>
      </tr>

      </table> 
	<input type="submit" value="Go">
      </form>

  
</body>
</html>
