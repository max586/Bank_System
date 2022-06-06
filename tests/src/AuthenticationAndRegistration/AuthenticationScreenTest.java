package src.AuthenticationAndRegistration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;

import java.sql.Statement;

import static org.junit.Assert.*;

public class AuthenticationScreenTest {
    AuthenticationScreen auth_screen;
    Statement st;
    String username,password,email,ordinary_account_number,savings_account_number;
    @Before
    public void initiate(){
        auth_screen = new AuthenticationScreen(null,null,null);
        st = Database.connectToDatabase("bank_system","root","password");
        username="new_user";
        password="password";
        email="email";
        ordinary_account_number="PL555";
        savings_account_number="PL333";
        Database.addUser(st,username,password,email);
        Database.addOrdinaryAccountNumber(st,username,ordinary_account_number);
        Database.addSavingsAccountNumber(st,username,savings_account_number);
        auth_screen.CreateScreen();
        auth_screen.frame.dispose();
    }
    @Test
    public void validUserTest(){
        auth_screen.usernameField.setText(username);
        auth_screen.passwordField.setText(password);
        auth_screen.signInButton.doClick();

        assertEquals(auth_screen.jpane.getMessage(),"user successfully verified");
        assertEquals(auth_screen.user.email,email);
        assertEquals(auth_screen.user.ordinary_account_number,ordinary_account_number);
        assertEquals(auth_screen.user.savings_account_number,savings_account_number);
    }
    @Test
    public void invalidUserTest(){
        auth_screen.usernameField.setText("qaqssw");
        auth_screen.passwordField.setText("paoal");
        auth_screen.signInButton.doClick();

        assertEquals(auth_screen.jpane.getMessage(),"user doesn't exist");
    }
    @After
    public void deleteUser(){
        Database.deleteUser(st,username);
    }
}