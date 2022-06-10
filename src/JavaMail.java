package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;


public class JavaMail{
   public static void SendMail(String recepient,String code) throws Exception {
      Scanner sc = new Scanner(System.in);
      File file = new File("/home/max/my_files/io/new_file");
      Scanner myReader = new Scanner(file);
      String myAccountEmail=myReader.nextLine();
      String password=myReader.nextLine();

      System.out.println("Preparing to send an email");
      Properties properties = new Properties();

      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", "outlook.office365.com");//send email from gmail domain
      properties.put("mail.smtp.port", "587");//587 port is usually used for smtp protocol

      Session session = Session.getInstance(properties,
              new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                 }
              });
      try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(myAccountEmail));
         message.setRecipients(Message.RecipientType.TO,
                 InternetAddress.parse(recepient));
         message.setSubject("Bank App verification code");
         message.setText("Hi,\nIt's your auto-generated code:\n" + code);
         Transport.send(message);
         System.out.println("Message successfully sent");

      } catch (MessagingException e) {
         System.out.println("Couldn't send a message");
         throw new RuntimeException(e);
      }
   }

   public static void main(String[] args) throws Exception {
      SendMail("maks.ovsienko2@gmail.com","123456");
      //System.out.println(System.console().readLine());
   }
}