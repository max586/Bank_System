package src;

import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static org.junit.Assert.*;

public class JavaMailTest {

    @Test(expected = RuntimeException.class)
    public void incorrectMailTest() throws Exception {
        JavaMail.SendMail("ab@c", "123456");
    }
    @Test(expected = Test.None.class)
    public void correctMailTest()throws Exception{
        JavaMail.SendMail("maks.ovsienko2@gmail.com","123456");
    }
}