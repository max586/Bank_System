package src;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connectToDatabase(String database_name,String username, String password){
        Connection con=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database_name,username,password); 
            System.out.println("Succesfully connected to "+ database_name+" database!"); 
        }catch(Exception e){ 
            System.out.println("Couldn't connect to "+database_name+" database!");
            System.out.println(e);}  
        return con;
    }
}
