package src.AuthenticationAndRegistration;

import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Statement;

public class RegistrationScreen1 extends Screen {
    public JPanel panel;
    public JTextField usernameField;
    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField repeatPasswordField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel usernameLabel;
    public JLabel emailLabel;
    public JLabel passwordLabel;
    public JLabel repeatPasswordLabel;
    public JLabel matchLabel;
    public JLabel lengthLabel;
    public JLabel letterLabel;
    public JLabel digitLabel;
    public JLabel specialCharLabel;
    public JLabel timerLabel;
    public int counter=0;

    public RegistrationScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }

    public void CreateScreen() {
        
        frame.setContentPane(panel);

        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Boolean password_is_valid = true, username_is_taken;
                user = new User();
                user.username = usernameField.getText();
                if (Database.isUsernameTaken( user.username)) {
                    username_is_taken = true;
                    usernameField.setText("username is already taken");
                } else {
                    usernameField.setText("ok");
                    username_is_taken = false;
                }
                user.password = new String(passwordField.getPassword());
                String repeated_password = new String(repeatPasswordField.getPassword());
                user.email = emailField.getText();

                if (DataValidation.passwordMatches(user.password, repeated_password)) {
                    matchLabel.setText("ok");
                } else {
                    matchLabel.setText("passwords dont match");
                    password_is_valid = false;
                }
                if (DataValidation.passwordLength(user.password)) {
                    lengthLabel.setText("ok");
                } else {
                    lengthLabel.setText("password too short");
                    password_is_valid = false;
                }
                if (DataValidation.letterInPassword(user.password)) {
                    letterLabel.setText("ok");
                } else {
                    letterLabel.setText("at least one upper or lowercase letter");
                    password_is_valid = false;
                }
                if (DataValidation.digitInPassword(user.password)) {
                    digitLabel.setText("ok");
                } else {
                    digitLabel.setText("at least one digit");
                    password_is_valid = false;
                }
                if (DataValidation.specialCharInPassword(user.password)) {
                    specialCharLabel.setText("ok");
                } else {
                    specialCharLabel.setText("at least one special character");
                    password_is_valid = false;
                }

                if (password_is_valid && !username_is_taken) {
                    //new_user.addUser(st);
                    frame.dispose();
                    if (next_screen != null) {
                        new EmailVerificationScreen(user, RegistrationScreen1.this, new RegistrationScreen2()).CreateScreen();
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

    public static void main(String[] args) {
        new RegistrationScreen1(null,null,new Screen()).CreateScreen();
    }
}
