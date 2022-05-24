package src;

import java.util.Properties;
import java.util.logging.*;
import javax.mail.*;
import javax.mail.internet.*;


public class JavaMail{

   public static void SendMail(String recepient,String code) throws Exception {
      System.out.println("Preparing to send an email");
      Properties properties = new Properties();

      properties.put("mail.smtp.auth","true");
      properties.put("mail.smtp.starttls.enable","true");
      properties.put("mail.smtp.host","smtp.gmail.com");//send email from gmail domain
      properties.put("mail.smtp.port","587");//587 port is usually used for smtp protocol

      String myAccountEmail = "azazsxsxkmkm@gmail.com";
      String password = "1qaz-pl,";

      Authenticator auth = new Authenticator() {
         //override the getPasswordAuthentication method
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, password);
         }
      };

      Session session = Session.getInstance(properties,auth);
      Message message = prepareMessage(session,myAccountEmail,recepient,code);
      Transport.send(message);
      System.out.println("Message successfully sent!");
   }

   private static Message prepareMessage(Session session, String myAccountEmail,String recepient,String code){
      try {
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(myAccountEmail));
         message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
         message.setSubject("Bank App verification code");
         message.setText("Hi,\nIt's your auto-generated code:\n"+code);
         return message;
      } catch (Exception e) {
         //TODO: handle exception
         Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE,null,e);
      }
      return null;
   }
}