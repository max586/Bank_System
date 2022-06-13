package src.Credits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Database;
import src.User;

import static org.junit.Assert.*;

public class CreditTest {

    Credit screen;
    User user;
    @Before
    public void initiate(){
        user = new User();
        user.username="new_user";
        user.password="password";
        user.email="email";
        user.ordinary_account_number="PL555";
        user.ordinary_account_balance=10000;
        user.savings_account_number="PL333";
        user.savings_account_balance=1000;
        Database.addUser(user.username,user.password,user.email,"1234");
        Database.addOrdinaryAccountNumber(user.username,user.ordinary_account_number);
        Database.setOrdinaryAccountBalance(user.username,user.ordinary_account_balance);
        Database.addSavingsAccountNumber(user.username,user.savings_account_number);
        Database.setSavingsAccountBalance(user.username,user.savings_account_balance);
        screen = new Credit(user,null,null);
        //screen.frame.dispose();
    }
    @Test
    public void alreadyHaveCredit(){
        Database.addCredit(user.username,1000,0,"2020-06-10",2);//add user credit
        screen.takeNewCreditButton.doClick();
        assertEquals("You already have a credit",screen.jpane.getMessage());
    }
    @Test
    public void takeNewCreditTest() {
        screen.takeNewCreditButton.doClick();
        assertEquals("you must accept the terms to take the credit",screen.jpane.getMessage());
        screen.yesCheckBox.setSelected(true);
        screen.Amount.setText("-1");
        screen.Years.setText("1");
        screen.takeNewCreditButton.doClick();
        assertEquals("You are allowed to type only numbers nad year must be from 1 to 50",screen.jpane.getMessage());
        screen.Amount.setText("12ab");
        screen.Years.setText("1");
        screen.takeNewCreditButton.doClick();
        assertEquals("You are allowed to type only numbers nad year must be from 1 to 50",screen.jpane.getMessage());
        screen.Amount.setText("1000");
        screen.Years.setText("12.789");
        screen.takeNewCreditButton.doClick();
        assertEquals("Now you have the credit",screen.jpane.getMessage());
        assertEquals(11000,screen.user.ordinary_account_balance,3);
        Database.deleteCredit(user.username);
        screen.Amount.setText("1000");
        screen.Years.setText("12");
        screen.takeNewCreditButton.doClick();
        assertEquals("Now you have the credit",screen.jpane.getMessage());
        assertEquals(12000,screen.user.ordinary_account_balance,3);
    }
    @Test
    public void checkDebt(){
        Database.addCredit(user.username,1000,0,"2020-06-10",2);//add user credit
        assertEquals(200,Float.parseFloat(screen.MyPayedCredit.getText()),3);
        assertEquals(0,Float.parseFloat(screen.MyDebt.getText()),3);
        screen.payDebtButton.doClick();
        assertEquals(550,Float.parseFloat(screen.MyPayedCredit.getText()),3);
        assertEquals(0,Float.parseFloat(screen.MyDebt.getText()),3);
    }
    @After
    public void deleteUser(){Database.deleteUser(user.username);}
}