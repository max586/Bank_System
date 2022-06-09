package src.AuthenticationAndRegistration;

import org.junit.Before;
import org.junit.Test;
import src.User;

import java.sql.Statement;

import static org.junit.Assert.*;

public class EmailVerificationScreenTest {
    EmailVerificationScreen screen;
    @Before
    public void initiate(){
        User user = new User();
        user.email="maks.ovsienko2@gmail.com";
        screen = new EmailVerificationScreen(user,null,null);
        screen.CreateScreen();
        screen.frame.dispose();
        screen.code="123456";
    }
    @Test
    public void incorrectEmail(){

    }
    @Test
    public void incorrectCode(){
        for(int i=0;i<4;i++){
            screen.codeField.setText("123567");
            screen.submitButton.doClick();
            screen.jdialog.dispose();
            assertEquals("Wrong code! "+(4-i)+" attempts left",screen.jpane.getMessage());
        }
    }
    @Test
    public void correctCode(){
        screen.codeField.setText("123456");
        screen.submitButton.doClick();
        screen.jdialog.dispose();
        assertEquals("Well done!!!",screen.jpane.getMessage());
    }
}