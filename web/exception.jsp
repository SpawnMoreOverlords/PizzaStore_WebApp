<%-- 
    Document   : exception
    Created on : 23-May-2014, 16:20:27
    Author     : Steve Hoult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="Beans.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<html>
<head><title>PizzaShop::pizza</title></head>
<body>
<h2>KAS Pizza Shop</h2>
<jsp:useBean id= "pizzaList" class= "Beans.ProductListBean" scope="application">
    Error, the bean should have been created in the servlet!
</jsp:useBean>


<c:set var="pizzalist_xslt">
   <c:import url="pizzalist_xslt.xsl"/>
</c:set> 

<x:transform xslt="${pizzalist_xslt}">
    <jsp:getProperty name="pizzaList" property="xml"/>
</x:transform>



<c:set var="shoppingcart_xslt">
   <c:import url="shoppingcart_xslt.xsl"/>
</c:set> 
<x:transform xslt="${shoppingcart_xslt}">
   <pizzashop:shoppingcart/>
</x:transform>

<c:if test="${sessionScope.currentUser != null}">
  <form action=shop?action=profile method=post>
    <input type="submit" value="Update Profile">
  </form>

  <form action=shop?action=logout method=post>
    <input type="submit" value="Logout">
  </form>
</c:if>
</body>
</html>

