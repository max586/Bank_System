package src;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataValidationTest {
    @Test
    public void passwordTest(){
        assertEquals(false,DataValidation.passwordMatches("qwerty","awerty"));
        assertEquals(false,DataValidation.digitInPassword("agjqghaq"));
        assertEquals(false,DataValidation.letterInPassword("257./../"));
        assertEquals(false,DataValidation.specialCharInPassword("12517sgqsgs"));
        assertEquals(true,DataValidation.passwordIsValid("W:9P%tsy3qWK","W:9P%tsy3qWK"));
        assertEquals(true,DataValidation.passwordIsValid("6D`vAa@'.VCYanf8","6D`vAa@'.VCYanf8"));
    }
    @Test
    public void peselTest(){
        assertEquals(false,DataValidation.peselIsValid("ajaj21626281728",true));
        assertEquals(false,DataValidation.peselIsValid("12345678910",true));
        assertEquals(false,DataValidation.peselIsValid("76011649558",false));
        assertEquals(true,DataValidation.peselIsValid("76011649558",true));
        assertEquals(true,DataValidation.peselIsValid("64060581862",false));
    }
    @Test
    public void nameTest(){
        assertEquals(false,DataValidation.nameIsValid("max"));
        assertEquals(false,DataValidation.nameIsValid("Max123"));
        assertEquals(false,DataValidation.nameIsValid("Ma"));
        assertEquals(true,DataValidation.nameIsValid("Max"));
    }
    @Test
    public void cityTest(){

    }
    @Test
    public void postcodeTest(){
        assertEquals(false,DataValidation.postcodeIsValid("ab-cde"));
        assertEquals(false,DataValidation.postcodeIsValid("12345"));
        assertEquals(false,DataValidation.postcodeIsValid("123-45"));
        assertEquals(true,DataValidation.postcodeIsValid("12-345"));
    }
    @Test
    public void phoneNrIsValid(){
        assertEquals(false,DataValidation.phoneNrIsValid("123 456 789"));
        assertEquals(false,DataValidation.phoneNrIsValid("+48 123456789"));
        assertEquals(true,DataValidation.phoneNrIsValid("123456789"));
    }
    @Test
    public void streetNrTest(){
        assertEquals(false,DataValidation.streetNrIsValid("12a"));
        assertEquals(false,DataValidation.streetNrIsValid("12.56"));
        assertEquals(true,DataValidation.streetNrIsValid("12/34"));
    }
    @Test
    public void isNumberTest(){
        assertEquals(false,DataValidation.isNumber("abc123"));
        assertEquals(false,DataValidation.isNumber("000123"));
        assertEquals(true,DataValidation.isNumber("1245.0890"));
    }

}