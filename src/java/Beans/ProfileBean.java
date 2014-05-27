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
public class ProfileBean {
    
    // create a profile bean

    private String url=null;
    private String user;
    private String password;
    private String name;
    private String street;
    private String zip;
    private String city;
    private String country;
    private HashMap<String,Boolean> role = null;

    // constructor, set the database URL

    public ProfileBean(String _url) {
        url=_url;
    }

    public HashMap<String, Boolean> getRole() {
	return role;
    }

    public void setRole(HashMap<String,Boolean> _role) {
        role = _role;
    }


    public String getUser() {
	return user;
    }

    public void setUser(String _user) {
        user = _user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String _pass) {
        password = _pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String _street) {
        street = _street;
    }

    public String getZip() {
        return zip;
    }


    public void setZip(String _zip) {
         zip = _zip;
    }
    public String getCity() {
        return city;
    }


    public void setCity(String _city) {
        city = _city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String _country) {
        country =  _country;
    }

    // populate a profile instance from the database

    public void populate(String u)  throws Exception{
        Connection conn =null;
        Statement stmt = null;
        ResultSet rs=null;
 
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT * from  CUSTOMER where USERNAME = " +
                                             "'" + u + "'";
            rs= stmt.executeQuery(sql);

	    // analyze the result set
	    
	    rs.next();
            user= u;
            password = rs.getString("PASSWORD");
            name = rs.getString("NAME");
            street = rs.getString("STREET");
            zip = rs.getString("ZIPCODE");
            city = rs.getString("CITY");
	    country = rs.getString("COUNTRY");
	}   
	catch(SQLException sqle){
            throw new Exception(sqle);
	}
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
    
    // return all roles that we have, return a MAP with names
    // and the flag set to false

     public HashMap<String,Boolean> getRoles() throws Exception {

        Connection conn =null;
        Statement stmt = null;
        ResultSet rs=null;
        HashMap<String,Boolean> role = new HashMap<String,Boolean>();

        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT * from USER_ROLES";
            rs= stmt.executeQuery(sql);
            while (rs.next()) {
		String r = rs.getString("ROLE_NAME");
		if(!role.containsKey(r))role.put(r,false);
            }
            return role;
	}   
	catch(SQLException sqle){
            throw new Exception(sqle);
	}
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
    
    // test if a user if in our tables

    public boolean testUser(String u) throws Exception {
	
        Connection conn =null;
        Statement stmt = null;
        ResultSet rs=null;
  
        try{
	    Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT NAME from CUSTOMER WHERE USERNAME = " + "'" + u + "'";
            rs= stmt.executeQuery(sql);

	    // check if we got any result set
	    
	    boolean b = rs.next();
            return b;
       }   
	catch(SQLException sqle){
            throw new Exception(sqle);
	}
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
}


