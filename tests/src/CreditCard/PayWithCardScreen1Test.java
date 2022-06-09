package src.CreditCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;
import src.User;

import java.sql.Statement;

import static org.junit.Assert.*;

public class PayWithCardScreen1Test {
    PayWithCardScreen1 screen;
    String username,password,email,card,pin;
    @Before
    public void initiate(){
        username="new_user";
        password="6X-q(A(s?g!k";
        email="@email";
        card="1234567887654321";
        pin="1234";
        Database.addUser(username,password,email,pin);
        Database.addCard(username,card,pin);
        screen = new PayWithCardScreen1(new User(),null,null);
        screen.CreateScreen();
        screen.frame.dispose();
    }
    @Test
    public void incorrectCardNr(){
        screen.nrField.setText("666");
        screen.submitButton.doClick();
        assertEquals("wrong card number or pin",screen.jpane.getMessage());
    }
    @Test
    public void incorrectPIN(){
        screen.nrField.setText(card);
        screen.pinField.setText("1235");
        screen.submitButton.doClick();
        assertEquals("wrong card number or pin",screen.jpane.getMessage());
    }
    @Test
    public void correctData(){
        screen.nrField.setText(card);
        screen.pinField.setText(pin);
        screen.submitButton.doClick();
        assertEquals("card successfully verified",screen.jpane.getMessage());
    }
    @After
    public void deleteUser(){Database.deleteUser(username);}
}