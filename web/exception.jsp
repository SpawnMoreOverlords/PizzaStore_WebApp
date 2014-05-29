<%-- 
    Document   : exception
    Created on : 23-May-2014, 16:20:27
    Author     : Steve Hoult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="Beans.*,Tags.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="pizzashop" uri="/WEB-INF/tlds/pizzashop"%>
<html>
<head><title>PizzaShop::pizza</title></head>
<body>
<h2>KAS Pizza Shop</h2>
<jsp:useBean id= "pizzaList" class= "Beans.ProductListBean" scope="application">
    Error, the bean should have been created in the servlet!
</jsp:useBean>


<c:set var="pizzalistsheet">
   <c:import url="pizzalistsheet.xsl"/>
</c:set> 

<x:transform xslt="${pizzalistsheet}">
    <jsp:getProperty name="pizzaList" property="xml"/>
</x:transform>


/*
<c:set var="shoppingcartsheet">
   <c:import url="shoppingcartsheet.xsl"/>
</c:set> 
<x:transform xslt="${shoppingcartsheet}">
   <pizzashop:shoppingcart/>
</x:transform>
*/    
    
<c:if test="${sessionScope.currentUser != null}">
  <form action=pizzaServlet?action=profile method=post>
    <input type="submit" value="Update Profile">
  </form>

  <form action=pizzaServlet?action=logout method=post>
    <input type="submit" value="Logout">
  </form>
</c:if>
</body>
</html>

