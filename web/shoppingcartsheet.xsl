<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : shoppingcartsheet.xsl
    Created on : 2014年5月28日, 下午9:48
    Author     : Kimi
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
   <xsl:template match="shoppingcart">
  <br /> <br />
  <table border="0" cellspacing="0">
    <tr bgcolor="silver">
    <td colspan="4">
        <strong>Shoppingcart</strong>
    </td>
    <tr bgcolor="silver">
      <td>Name</td>
      <td>Quantity</td>
      <td colspan="2">Remove</td>
    </tr>
    </tr>
        <xsl:apply-templates/>
      <tr>
      <td colspan="2">
        <a href="pizzaServlet?action=checkout">Checkout</a>
      </td>
    </tr>
   </table>
  </xsl:template>
  <xsl:template match="order">
  <form method="post" action="shop">
    <tr>
        <td>
            <xsl:value-of select="pizza/name"/>
        </td>
        <td align="right">
            <xsl:value-of select="quantity"/>
        </td>

        <td>
            <xsl:element name="input"> <!--A ordinary input in XSLT-->
              <xsl:attribute name="size">2</xsl:attribute>
              <xsl:attribute name="type">text</xsl:attribute>
              <xsl:attribute name="value">1</xsl:attribute>
              <xsl:attribute name="name">quantity</xsl:attribute>
            </xsl:element>        
        </td>
        <td>
            <input type="submit" value="Remove"/>
        </td>
    
      <xsl:element name="input"> <!--A ordinary input in XSLT-->
        <xsl:attribute name="type">hidden</xsl:attribute>
        <xsl:attribute name="value"><xsl:value-of select="pizza/name"/></xsl:attribute>
        <xsl:attribute name="name">pizzaname</xsl:attribute>
      </xsl:element>
      <xsl:element name="input"> <!--A ordinary input in XSLT-->
        <xsl:attribute name="type">hidden</xsl:attribute>
        <xsl:attribute name="value">remove</xsl:attribute>
        <xsl:attribute name="name">action</xsl:attribute>
      </xsl:element>

    </tr>
    </form>
  </xsl:template>


</xsl:stylesheet>
