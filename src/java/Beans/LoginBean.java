
package Beans;
 import java.sql.*;

public class LoginBean {

    private String username;
    private String password;

    public void setUsername(String _Username){
        this.username = _Username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String _password){
        this.password = _password;
    }
    public String getPassword(){
        return this.password;
    }
    public boolean checklogin() {
        boolean loginflag = false;
        
        System.out.println("ATTEMPTING TO CONNECT TO DB!!");
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/aleks", "aleks", "12345");
            if(con==null){
            System.out.println("dababase connection fail");
            }
            else{System.out.println("dababase connection success");}
            stmt = con.createStatement();
            String sql = "select * from USERS where USERS.username=" + "'" + username + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    loginflag = true;
                }
            }
        } catch (Exception sqlex1) {
            sqlex1.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqlex2) {
                    sqlex2.printStackTrace();
                }

            }
        }

        return loginflag;
        //test
    }
}
