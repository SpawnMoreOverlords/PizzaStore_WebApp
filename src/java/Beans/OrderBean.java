/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;
import java.sql.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Kimi
 */
public class OrderBean  {

  private Connection con;
  private PreparedStatement orderPstmt;
  private PreparedStatement orderItemPstmt;
  private PreparedStatement stmt = null;
  private ResultSet rs=null;

  private String url;
  private ShoppingBean sb;
  private String buyerName;
  private String shippingAddress;
  private String shippingZipcode;
  private String shippingCity;

    private static String orderSQL;
    private static String orderItemSQL;

  public OrderBean(String _url, ShoppingBean _sb, String _buyerName, 
		   String _shippingAddress, String _shippingZipcode, 
		   String _shippingCity){
    url = _url;
    sb = _sb;
    buyerName=_buyerName;
    shippingAddress=_shippingAddress;
    shippingZipcode=_shippingZipcode;
    shippingCity=_shippingCity;
  }

  /**
   * Saves an order in the database
   */
  public void saveOrder() throws Exception{
    orderSQL="INSERT INTO ORDERS(USERNAME,";
    orderSQL += " SHIPPING_ADDRESS, SHIPPING_ZIPCODE, SHIPPING_CITY)";
    orderSQL += " VALUES(?,?,?,?)";
    try{

	// load the driver and get a connection

	Class.forName("com.mysql.jdbc.Driver");
	con = DriverManager.getConnection(url);

	// turn off autocommit to handle transactions yourself

	con.setAutoCommit(false);
	orderPstmt = con.prepareStatement(orderSQL);
	orderPstmt.setString(1, buyerName);
	orderPstmt.setString(2, shippingAddress);
	orderPstmt.setString(3, shippingZipcode);
	orderPstmt.setString(4, shippingCity);
	orderPstmt.execute();

	// now handle all items in the cart

	saveOrderItems();
	sb.clear();
	con.commit();  // end the transaction
    }
    catch(Exception e){
	try{
	    con.rollback();    // failed, rollback the database
	}
	catch(Exception ee){}
	throw new Exception("Error saving Order", e);
    }
    finally{
	try {
	    rs.close();
	}
	catch(Exception e) {}       
	try {
	    stmt.close();
	}
	catch(Exception e) {}
	try{
	    orderPstmt.close();
	}
	catch(Exception e){}
	try{
	    orderItemPstmt.close();
	}
	catch(Exception e){}
	try{
	    con.close();
	}
	catch(Exception e){}
    }
  }

/**
 * Saves the different items in the order
 */
  private void saveOrderItems() throws Exception{

      // get the value of the last stored AUTO_INCREMENT variable
      // i. e. ORDER_ID

      orderItemSQL="INSERT INTO ORDERPRODUCT(ORDERID, ";
      orderItemSQL += "PRODUCTNAME, QUANTITY) VALUES (?,?,?)";
      stmt = con.prepareStatement("SELECT LAST_INSERT_ID()");
      rs = stmt.executeQuery();
      rs.next();
      int orderId=rs.getInt(1);

      Iterator iter = ((Collection)sb.getCart()).iterator();
      PizzaBean pb = null;
      Object tmpArr[];

      //Loop over the entire collection, i.e the entire shopping cart
 
      orderItemPstmt = con.prepareStatement(orderItemSQL);
      while(iter.hasNext()){
          
	  tmpArr = (Object[])iter.next();
	  pb = (PizzaBean)tmpArr[0];
          orderItemPstmt.setInt(1,orderId);
          orderItemPstmt.setString(2,pb.getName());
          orderItemPstmt.setInt(3,((Integer)tmpArr[1]).intValue());  
          orderItemPstmt.execute();
      }
  }
}




