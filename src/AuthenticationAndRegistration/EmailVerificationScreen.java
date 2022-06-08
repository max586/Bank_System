package src.AuthenticationAndRegistration;

import src.JavaMail;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class EmailVerificationScreen extends Screen{
    public JPanel panel;
    public JTextField codeField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel descrLabel;
    public JLabel timerLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    public int number_of_attempts=5;
    public String code;
    public int counter=0;

    public EmailVerificationScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        jdialog=jpane.createDialog(panel,"");
    }
    public void CreateScreen() {
        code = generateCode(6);
        try {
            JavaMail.SendMail("","",user.email, code);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("failed to send an email");
            System.out.println(e);
        }
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(codeField.getText().equals(code)){
                    jpane.setMessage("Well done!!!");
                    jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    jdialog.setTitle("Info");
                    jdialog.setVisible(true);
                    frame.dispose();
                    if(next_screen!=null){
                        frame.dispose();
                        next_screen.user=user;
                        next_screen.prev_screen = EmailVerificationScreen.this;
                        next_screen.next_screen = new Screen();
                        next_screen.CreateScreen();
                    }
                }
                else{
                    number_of_attempts--;
                    if(number_of_attempts>0){
                        jpane.setMessage("Wrong code! "+number_of_attempts+" attempts left");
                        jpane.setMessageType(JOptionPane.WARNING_MESSAGE);
                        jdialog.setTitle("Warning");
                        jdialog.setVisible(true);
                    }
                    else{
                        frame.dispose();
                        if(prev_screen!=null){
                            prev_screen.frame.setVisible(true);
                        }
                    }
                }
            }
        });
        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                if(prev_screen!=null){
                    prev_screen.frame.setVisible(true);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {counter=0;}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {counter=0;}
            @Override
            public void mouseEntered(MouseEvent e) {counter=0;}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        new Thread() {
            public void run() {
                while (counter <= 120) {
                    if(!frame.isDisplayable()){counter=0;}
                    else {
                        timerLabel.setText("Time before log out: " + (120 - counter++));
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
                frame.dispose();
            }
        }.start();
        frame.setSize(800,600);
        frame.setVisible(true);
    }
    public static String generateCode(int length){
        String code="";
        Random rnd = new Random();
        for(int i=0;i<length;i++){
            code+=Integer.toString(rnd.nextInt(9));
        }
        return code;
    }

    public static void main(String[] args) {
        User user = new User();
        user.username = "test_user";
        user.password = "password";
        user.email = "maks.ovsienko2@gmail.com";
        new EmailVerificationScreen(user,null,new RegistrationScreen2()).CreateScreen();
    }
}
