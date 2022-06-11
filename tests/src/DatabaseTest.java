package src;

import org.junit.After;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Test
    public void addUser() throws SQLException {
        String username="abc",password="shiquhi1212",email="abc@com",appcode="1234";
        ResultSet rs = Database.st.executeQuery("select count(*) from Users where username='"+username+"';");
        rs.next();
        assertEquals(0,rs.getInt(1));
        Database.addUser(username,password,email,appcode);
        rs = Database.st.executeQuery("select count(*) from Users where username='"+username+"'and password='"+password+"'and email='"+email+"'and appCode='"+appcode+"';");
        rs.next();
        assertEquals(1,rs.getInt(1));
        Database.deleteUser(username);
    }

    @Test
    public void addUserData() {
    }

    @Test
    public void addOrdinaryAccountNumber() {
    }

    @Test
    public void addCard() {
    }

    @Test
    public void addToHistory() {
    }
}