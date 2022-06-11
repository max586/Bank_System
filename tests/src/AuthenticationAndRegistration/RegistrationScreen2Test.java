package src.AuthenticationAndRegistration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import src.Database;
import src.User;

import java.sql.Statement;

import static org.junit.Assert.*;

public class RegistrationScreen2Test {
    RegistrationScreen2 screen;
    User user;

    @Before
    public void initiate(){
        user = new User();
        user.username="new_user";
        user.password="6X-q(A(s?g!k";
        user.email="@email";
        
        screen = new RegistrationScreen2(user,null,null);
        screen.CreateScreen();
        screen.frame.dispose();
    }
    @Test
    public void invalidFirstName(){
        screen.firstNameField.setText("An");
        screen.submitButton.doClick();
        assertEquals("First name is invalid",screen.firstNameField.getText());
        screen.firstNameField.setText("anaty");
        screen.submitButton.doClick();
        assertEquals("First name is invalid",screen.firstNameField.getText());
        screen.firstNameField.setText("klttt");
        screen.submitButton.doClick();
        assertEquals("First name is invalid",screen.firstNameField.getText());
    }
    @Test
    public void invalidLastName(){
        screen.lastNameField.setText("An");
        screen.submitButton.doClick();
        assertEquals("Last name is invalid",screen.lastNameField.getText());
        screen.lastNameField.setText("anaty");
        screen.submitButton.doClick();
        assertEquals("Last name is invalid",screen.lastNameField.getText());
        screen.lastNameField.setText("klttt");
        screen.submitButton.doClick();
        assertEquals("Last name is invalid",screen.lastNameField.getText());
    }
    @Test
    public void invalidStreet(){
        screen.streetField.setText("");
        screen.submitButton.doClick();
        assertEquals("Street is invalid",screen.streetField.getText());
        screen.streetField.setText("Re1t");
        screen.submitButton.doClick();
        assertEquals("Street is invalid",screen.streetField.getText());
        screen.streetField.setText("abcd");
        screen.submitButton.doClick();
        assertEquals("Street is invalid",screen.streetField.getText());
    }
    @Test
    public void invalidStreetNr(){
        screen.streetNrField.setText("");
        screen.submitButton.doClick();
        assertEquals("Street number is invalid",screen.streetNrField.getText());
        screen.streetNrField.setText("12/abc");
        screen.submitButton.doClick();
        assertEquals("Street number is invalid",screen.streetNrField.getText());
        screen.streetNrField.setText("12 21");
        screen.submitButton.doClick();
        assertEquals("Street number is invalid",screen.streetNrField.getText());
    }
    @Test
    public void invalidCity(){
        screen.cityField.setText("abcd");
        screen.submitButton.doClick();
        assertEquals("City is invalid",screen.cityField.getText());
        screen.cityField.setText("Aui76..");
        screen.submitButton.doClick();
        assertEquals("City is invalid",screen.cityField.getText());
        screen.cityField.setText("A");
        screen.submitButton.doClick();
        assertEquals("City is invalid",screen.cityField.getText());
    }
    @Test
    public void invalidPesel(){
        screen.sexComboBox.setSelectedItem("M");
        screen.peselField.setText("sioa./.,1234");
        screen.submitButton.doClick();
        assertEquals("PESEL is invalid",screen.peselField.getText());
        screen.streetField.setText("123456789101");
        screen.submitButton.doClick();
        assertEquals("PESEL is invalid",screen.peselField.getText());
        screen.streetField.setText("92030786312");
        screen.submitButton.doClick();
        assertEquals("PESEL is invalid",screen.peselField.getText());
    }
    @Test
    public void invalidPhoneNr(){
        screen.phoneNrField.setText("123 456 789");
        screen.submitButton.doClick();
        assertEquals("Phone nr is invalid",screen.phoneNrField.getText());
        screen.phoneNrField.setText("+48123456789");
        screen.submitButton.doClick();
        assertEquals("Phone nr is invalid",screen.phoneNrField.getText());
        screen.phoneNrField.setText("abc");
        screen.submitButton.doClick();
        assertEquals("Phone nr is invalid",screen.phoneNrField.getText());
    }
    @Test
    public void validData(){
        screen.firstNameField.setText("Abc");
        screen.lastNameField.setText("Def");
        screen.sexComboBox.setSelectedItem("M");
        screen.streetField.setText("Abcdef");
        screen.streetNrField.setText("123/321");
        screen.cityField.setText("Qwerty");
        screen.peselField.setText("92030786311");
        screen.phoneNrField.setText("123456789");
        screen.submitButton.doClick();
        assertEquals("ok",screen.firstNameField.getText());
        assertEquals("ok",screen.lastNameField.getText());
        assertEquals("ok",screen.streetField.getText());
        assertEquals("ok",screen.streetNrField.getText());
        assertEquals("ok",screen.cityField.getText());
        assertEquals("ok",screen.peselField.getText());
        assertEquals("ok",screen.phoneNrField.getText());
    }
    @After
    public void deleteUser(){Database.deleteUser(user.username);}

}