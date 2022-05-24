package src;

import java.sql.*;

public class DatabaseConnection {
    public static Statement connectToDatabase(String database_name,String username, String password){
        Connection con=null;
        Statement st=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database_name,username,password); 
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            System.out.println("Succesfully connected to "+ database_name+" database!"); 
        }catch(Exception e){ 
            System.out.println("Couldn't connect to "+database_name+" database!");
            System.out.println(e);}  
        return st;
    }
}
