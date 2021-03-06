package src.transfers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;
import src.User;
import src.mainFrame.MainFrame;

import static org.junit.Assert.*;

public class StandardTransferTest {
    StandardTransfer screen;
    User user;
    @Before
    public void initiate()throws java.io.IOException,java.awt.FontFormatException{
        user = new User();
        user.username="new_user";
        user.password="password";
        user.email="email";
        user.ordinary_account_number="PL555";
        user.ordinary_account_balance=10000;
        user.savings_account_number="PL333";
        user.savings_account_balance=10000;
        Database.addUser(user.username,user.password,user.email,"1234");
        Database.addOrdinaryAccountNumber(user.username,user.ordinary_account_number);
        Database.addSavingsAccountNumber(user.username,user.savings_account_number);
        screen = new StandardTransfer(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame());
        screen.frame.getjFrame().dispose();
    }
    @Test
    public void emptyAccountNr(){
        screen.accountNumberTxt.setText("");
        screen.nextButton.doClick();
        assertEquals(true,screen.accountNumberWarning.isVisible());
    }
    @Test
    public void wrongAccountNr(){
        screen.accountNumberTxt.setText("abc");
        screen.nextButton.doClick();
        assertEquals(true,screen.accountNumberWarning.isVisible());
    }
    @Test
    public void correctAccountNr(){
        screen.accountNumberTxt.setText(user.ordinary_account_number.substring(2));
        screen.nextButton.doClick();
        assertEquals(false,screen.accountNumberWarning.isVisible());
    }
    @After
    public void deleteUser(){Database.deleteUser(user.username);}

}