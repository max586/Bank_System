package src.AuthenticationAndRegistration;

import src.*;
import javax.swing.*;    
import java.awt.event.*;
import java.sql.Statement;
import java.awt.Insets;

public class RegistrationScreen1 extends Screen{
    JTextArea description;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel loginLabel;
    JTextField loginField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JLabel repeatPasswordLabel; 
    JPasswordField repeatPasswordField;
    JLabel passwordMatchesField;
    JLabel passwordLengthField;
    JLabel passwordLetterField;
    JLabel passwordDigitField;
    JLabel passwordSpecialCharField;
    JLabel emailLabel;
    JTextField emailField;
    JButton submitButton;


    public RegistrationScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Registration screen1");
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        usernameLabel=new JLabel("username");
        gbc.insets = new Insets(10,5,5,10);
        frame.add(usernameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        usernameField=new JTextField();
        gbc.insets = new Insets(10,10,5,5);
        frame.add(usernameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        passwordLabel=new JLabel("password");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(passwordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        passwordField=new JPasswordField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(passwordField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        repeatPasswordLabel=new JLabel("enter again your password");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(repeatPasswordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=2;
        repeatPasswordField = new JPasswordField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(repeatPasswordField,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=3;
        passwordMatchesField = new JLabel();
        passwordMatchesField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordMatchesField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=3;
        passwordLengthField = new JLabel();
        passwordLengthField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLengthField,gbc);

        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridwidth=3;
        passwordLetterField = new JLabel();
        passwordLetterField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLetterField,gbc);

        gbc.gridx=0;
        gbc.gridy=6;
        gbc.gridwidth=3;
        passwordDigitField = new JLabel();
        passwordDigitField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordDigitField,gbc);

        gbc.gridx=0;
        gbc.gridy=7;
        gbc.gridwidth=3;
        passwordSpecialCharField = new JLabel();
        passwordSpecialCharField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordSpecialCharField,gbc);

        gbc.gridx=0;
        gbc.gridy=8;
        gbc.gridwidth=1;
        emailLabel=new JLabel("email");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(emailLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridwidth=2;
        emailField=new JTextField();
        gbc.insets = new Insets(5,10,10,5);
        frame.add(emailField,gbc);

        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridwidth=1;
        Statement st = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Boolean password_is_valid=true,username_is_taken;
                user = new User();
                user.username = usernameField.getText();
                if(user.isUsernameTaken(st,user.username)){
                    username_is_taken=true;
                    usernameField.setText("username is already taken");
                }
                else{username_is_taken=false;}
                user.password = new String(passwordField.getPassword());
                String repeated_password = new String(repeatPasswordField.getPassword());
                user.email = emailField.getText();

                passwordMatchesField.setVisible(true);
                passwordLengthField.setVisible(true);
                passwordLetterField.setVisible(true);
                passwordSpecialCharField.setVisible(true);
                passwordDigitField.setVisible(true);

                if(DataValidation.passwordMatches(user.password, repeated_password)){passwordMatchesField.setText("ok");}
                else{passwordMatchesField.setText("passwords dont match");password_is_valid=false;}
                if(DataValidation.passwordLength(user.password)){passwordLengthField.setText("ok");}
                else{passwordLengthField.setText("password too short");password_is_valid=false;}
                if(DataValidation.letterInPassword(user.password)){passwordLetterField.setText("ok");}
                else{passwordLetterField.setText("at least one upper or lowercase letter");password_is_valid=false;}
                if(DataValidation.digitInPassword(user.password)){passwordDigitField.setText("ok");}
                else{passwordDigitField.setText("at least one digit");password_is_valid=false;}
                if(DataValidation.specialCharInPassword(user.password)){passwordSpecialCharField.setText("ok");}
                else{passwordSpecialCharField.setText("at least one special character");password_is_valid=false;}

                if(password_is_valid && !username_is_taken){
                    //new_user.addUser(st);
                    frame.dispose();
                    if(next_screen!=null){
                        new EmailVerificationScreen(user, RegistrationScreen1.this, new RegistrationScreen2()).CreateScreen();
                    }
                }
            }
        });
        gbc.insets = new Insets(10,10,10,10);
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridwidth=1;
        gbc.insets = new Insets(10,10,10,10);
        frame.add(exitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=10;
        frame.add(returnButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //CreateScreen();
    }

}
