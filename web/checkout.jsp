<%-- 
    Document   : checkout
    Created on : 23-May-2014, 16:19:02
    Author     : Steve Hoult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="Beans.*,Tags.*"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="pizzashop" uri="/WEB-INF/tlds/pizzashop"%>

<c:if test="${sessionScope.currentUser == null}">
  <pizzashop:profile url="${jdbcURL}"/>
</c:if>
<c:set var="shoppingcart_xslt">
   <c:import url="checkoutsheet.xsl"/>
</c:set> 
<x:transform xslt="${shoppingcart_xslt}">
   <checkout>
   <pizzashop:shoppingcart/>
   <name>${profile.name}</name>
   <address>${profile.street}</address>
   <zip>${profile.zip}</zip>
   <city>${profile.city}</city>
   </checkout>
</x:transform>
