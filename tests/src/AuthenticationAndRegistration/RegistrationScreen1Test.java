package src.AuthenticationAndRegistration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;

import java.sql.Statement;

import static org.junit.Assert.*;

public class RegistrationScreen1Test {
    RegistrationScreen1 reg_screen;
    Statement st;
    String username,password,email,ordinary_account_number,savings_account_number;

    @Before
    public void initiate(){
        reg_screen = new RegistrationScreen1(null,null,null);

        username="new_user";
        password="6X-q(A(s?g!k";
        email="@email";
        Database.addUser(username,password,email,"1234");
        Database.addOrdinaryAccountNumber(username,ordinary_account_number);
        Database.addSavingsAccountNumber(username,savings_account_number);
        reg_screen.CreateScreen();
        reg_screen.frame.dispose();
    }
    @Test
    public void takenUsername(){
        reg_screen.usernameField.setText(username);
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.usernameField.getText(),"username is already taken");
    }
    @Test
    public void dontMatchPassword(){
        reg_screen.passwordField.setText("swjnsw");
        reg_screen.repeatPasswordField.setText("swjnsv");
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.matchLabel.getText(),"passwords dont match");
    }
    @Test
    public void shortPassword(){
        reg_screen.passwordField.setText("swjnsw");
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.lengthLabel.getText(),"password too short");
    }
    @Test
    public void noLetterPassword(){
        reg_screen.passwordField.setText("1279127912.././");
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.letterLabel.getText(),"at least one upper or lowercase letter");
    }
    @Test
    public void noDigitPassword(){
        reg_screen.passwordField.setText("swjnsw");
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.digitLabel.getText(),"at least one digit");
    }
    @Test
    public void noSpecialCharPassword(){
        reg_screen.passwordField.setText("swjnsw");
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.specialCharLabel.getText(),"at least one special character");
    }
    @Test
    public void validData(){
        Database.deleteUser(username);
        reg_screen.usernameField.setText(username);
        reg_screen.passwordField.setText(password);
        reg_screen.repeatPasswordField.setText(password);
        reg_screen.emailField.setText(email);
        reg_screen.submitButton.doClick();

        assertEquals(reg_screen.usernameField.getText(),"ok");
        assertEquals(reg_screen.matchLabel.getText(),"ok");
        assertEquals(reg_screen.lengthLabel.getText(),"ok");
        assertEquals(reg_screen.letterLabel.getText(),"ok");
        assertEquals(reg_screen.digitLabel.getText(),"ok");
        assertEquals(reg_screen.specialCharLabel.getText(),"ok");
    }
    @After
    public void deleteUser(){Database.deleteUser(username);}
}