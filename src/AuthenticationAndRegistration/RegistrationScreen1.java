package src.AuthenticationAndRegistration;

import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class RegistrationScreen1 extends Screen {
    private JPanel panel;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JButton submitButton;
    private JButton returnButton;
    private JButton exitButton;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JLabel matchLabel;
    private JLabel lengthLabel;
    private JLabel letterLabel;
    private JLabel digitLabel;
    private JLabel specialCharLabel;

    public RegistrationScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }

    public void CreateScreen() {
        
        frame.setContentPane(panel);

        Statement st = Database.connectToDatabase("bank_system", "root", "password");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Boolean password_is_valid = true, username_is_taken;
                user = new User();
                user.username = usernameField.getText();
                if (Database.isUsernameTaken(st, user.username)) {
                    username_is_taken = true;
                    usernameField.setText("username is already taken");
                } else {
                    username_is_taken = false;
                }
                user.password = new String(passwordField.getPassword());
                String repeated_password = new String(repeatPasswordField.getPassword());
                user.email = emailField.getText();

                /*matchLabel.setVisible(true);
                lengthLabel.setVisible(true);
                letterLabel.setVisible(true);
                specialCharLabel.setVisible(true);
                digitLabel.setVisible(true);*/

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
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new RegistrationScreen1(null,null,new Screen()).CreateScreen();
    }
}
