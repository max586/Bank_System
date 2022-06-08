package src;

import javax.xml.transform.Result;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class User {
    //attributes may not be initialized
    public String username;
    public String password;
    public String email;
    public String appCode;
    public String firstName;
    public String lastName;
    public String sex;
    public String phone_number;
    public String city;
    public String post_code;
    public String street;
    public int street_nr;
    public String pesel; 
    public String ordinary_account_number;
    public float ordinary_account_balance;
    public String savings_account_number;
    public float savings_account_balance;
    public String card_number;
    public String pin_code;

    public void displayUser(Statement st){
        System.out.println("User data:");
        System.out.println("username - "+username);
        System.out.println("password - "+password);
        System.out.println("email - "+email);
    }
}
