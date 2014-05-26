/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;
import java.util.*;
import java.sql.*;
import java.io.*;
/**
 *
 * @author Kimi
 */
public class ProductListBean {
     private Collection pizzaList;
    private String url=null;

    
    /** Creates a new instance of BookListBean */

    public ProductListBean(String _url) throws Exception {
        url=_url;
        Connection conn =null;
        Statement stmt = null;
        ResultSet rs = null;
        pizzaList = new ArrayList();    // a list
        try{
            
	    // get a database connection and load the JDBC-driver

            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
	    // create SQL statements to load the books into the list
	    // each book is a BookBean object

            stmt = conn.createStatement();
            String sql="SELECT NAME, PRICE ";
            sql += "DESCRIPTION FROM PRODUCT,";
            rs= stmt.executeQuery(sql);
            
	    // analyze the result set

            while(rs.next()){
                
                PizzaBean pb = new PizzaBean();
                
                pb.setName(rs.getString("NAME"));
                pb.setPrice(rs.getInt("PRICE"));
                pb.setDes(rs.getString("DESCRIPTION"));               
                pizzaList.add(pb);
                
            }
        
        }
        catch(SQLException sqle){
            throw new Exception(sqle);
        }

	// note the we always try to close all services
	// even if one or more fail to close
	
        finally{
 	    try{
              rs.close();
            }
            catch(Exception e) {}
            try{
              stmt.close();
            }
	    catch(Exception e) {}
            try {
              conn.close();
            }
            catch(Exception e){}
        }
    }
    
    // return the pizzalist
    
    java.util.Collection getProduktLista() {
        return pizzaList;
    }
    
    // create an XML document from the booklist

    public String getXml() {
        
        BookBean bb=null;
        Iterator iter = bookList.iterator();
        StringBuffer buff = new StringBuffer();
        
        buff.append("<booklist>");
        while(iter.hasNext()){
            bb=(BookBean)iter.next();
            buff.append(bb.getXml());
        }
        buff.append("</booklist>");        
        
        return buff.toString();
    }
    

    // search for a pizza by pizza Name

    public PizzaBean getById(String name) {
	PizzaBean pb = null;
	Iterator iter = pizzaList.iterator();
        
	while(iter.hasNext()){
	    pb=(PizzaBean)iter.next();
	    if(pb.getName()== name){
                return pb;
	    }
	}
	return null;
    }
    
    // a main used for testing, remember that a bean can be run
    // without a container

    public static void main(String[] args){
        try{
	    //ProductListBean plb = new ProductListBean();
	    //System.out.println(plb.getXml());
        }
        catch(Exception e){
	    System.out.println(e.getMessage());
        }
    }
}
