package src.CreditCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;
import src.User;

import java.sql.Statement;

import static org.junit.Assert.*;

public class PayWithCardScreen2Test {
    PayWithCardScreen2 screen;
    String username,password,email,card,pin,ord_nr,app_code;
    float balance;
    @Before
    public void initiate(){
        username="new_user";
        password="6X-q(A(s?g!k";
        email="@email";
        card="1234567887654321";
        pin="1234";
        ord_nr="PL123";
        app_code="1234";
        balance= 1000.0F;
        Database.addUser(username,password,email,app_code);
        Database.addCard(username,card,pin);
        Database.addOrdinaryAccountNumber(username,ord_nr);
        Database.setOrdinaryAccountBalance(username,balance);
        User user = new User();
        user.username=username;
        user.card_number=card;
        user.pin_code=pin;
        screen = new PayWithCardScreen2(user,null,null);
        screen.CreateScreen();
        screen.frame.dispose();
    }
    @Test
    public void incorecctAmount(){
        screen.paymentField.setText("-100");
        screen.submitButton.doClick();
        assertEquals("Incorrect payment amount",screen.jpane.getMessage());
        screen.paymentField.setText("1001");
        screen.submitButton.doClick();
        assertEquals("Too little money on account to make a payment",screen.jpane.getMessage());
    }
    @Test
    public void correctAmount(){
        screen.paymentField.setText("999.99");
        screen.submitButton.doClick();
        assertEquals("Payment successfully processed",screen.jpane.getMessage());
        assertEquals(0.01,Database.getOrdinaryAccountBalance(username),0.009);
    }
    @After
    public void deleteUser(){Database.deleteUser(username);}

}