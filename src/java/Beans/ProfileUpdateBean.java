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
public class ProfileUpdateBean {
    
    private String url=null;

    public ProfileUpdateBean(String _url) {
        url=_url;
    }

    // store a profile in the database

    public void setProfile(ProfileBean pb)  throws Exception{
        Connection conn =null;
        Statement stmt = null;
        int rs;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql, sql2, sql3;
            conn.setAutoCommit(false);
            sql ="UPDATE CUSTOMER SET PASSWORD = " + "'" + pb.getPassword();
	    sql += "'," + "NAME = " + "'" + pb.getName() + "',";
	    sql += "STREET = " + "'" + pb.getStreet() + "',";
	    sql += "ZIPCODE = " + "'" + pb.getZip() + "',";
            sql += "CITY = " + "'" + pb.getCity() + "',";
	    sql += "COUNTRY = " + "'" + pb.getCountry() + "'"; 
            sql += "WHERE USERNAME = " + "'" + pb.getUser() + "'";
            rs = stmt.executeUpdate(sql);
            sql3 = "DELETE from USER_ROLES where USER_NAME =";
	    sql3 += "'" + pb.getUser() + "'";
            rs = stmt.executeUpdate(sql3);
	    HashMap<String,Boolean> a = pb.getRole();
            Set<String> k = a.keySet();
            Iterator<String> i = k.iterator();
            while (i.hasNext()) {
		String st = i.next();
		if(a.get(st)) {
		    sql2 = "INSERT INTO USER_ROLES(USER_NAME, ROLE_NAME)";
		    sql2 += "VALUES (" + "'" + pb.getUser() + "', '" + st;
		    sql2 += "')";
		    rs = stmt.executeUpdate(sql2);
		}
	    }
	    conn.commit();
	}   
	catch(SQLException sqle){
            conn.rollback();
            throw new Exception(sqle);
	}
        finally{
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
    
    // store a new user
 
    public void setUser(ProfileBean pb)  throws Exception{
        Connection conn =null;
        Statement stmt = null;
        int rs;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql,sql2;
            conn.setAutoCommit(false);
            sql ="INSERT INTO CUSTOMER (USERNAME, PASSWORD, NAME,";
	    sql += "STREET, ZIPCODE, CITY, COUNTRY) VALUES ( ";
            sql += "'" + pb.getUser() + "', '" + pb.getPassword() + "', '";
	    sql += pb.getName() + "', '" + pb.getStreet() + "', '";
            sql += pb.getZip() +"', '" + pb.getCity() + "', '";
	    sql += pb.getCountry() + "')";
            rs = stmt.executeUpdate(sql);
            conn.commit();
	}   
	catch(SQLException sqle){
            conn.rollback();
            throw new Exception(sqle);
	}
        finally {
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


