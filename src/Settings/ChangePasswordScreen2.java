package src.Settings;

import src.DatabaseConnection;
import src.User;
import src.AuthenticationAndRegistration.DataValidation;
import src.Screen;

import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.Statement;

public class ChangePasswordScreen2 extends Screen{
    JLabel passwordLabel;
    JPasswordField passwordField;
    JLabel repeatPasswordLabel;
    JPasswordField repeatPasswordField;
    JLabel passwordMatchesField;
    JLabel passwordLengthField;
    JLabel passwordLetterField;
    JLabel passwordDigitField;
    JLabel passwordSpecialCharField;
    JButton submitButton;

    public ChangePasswordScreen2(){}
    public ChangePasswordScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Change password screen2");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        passwordLabel = new JLabel("Enter new password");
        frame.add(passwordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        passwordField = new JPasswordField();
        frame.add(passwordField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        repeatPasswordLabel = new JLabel("Repeat password");
        frame.add(repeatPasswordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        repeatPasswordField = new JPasswordField();
        frame.add(repeatPasswordField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=3;
        passwordMatchesField = new JLabel();
        passwordMatchesField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordMatchesField,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=3;
        passwordLengthField = new JLabel();
        passwordLengthField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLengthField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=3;
        passwordLetterField = new JLabel();
        passwordLetterField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLetterField,gbc);

        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridwidth=3;
        passwordDigitField = new JLabel();
        passwordDigitField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordDigitField,gbc);

        gbc.gridx=0;
        gbc.gridy=6;
        gbc.gridwidth=3;
        passwordSpecialCharField = new JLabel();
        passwordSpecialCharField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordSpecialCharField,gbc);

        gbc.gridx=1;
        gbc.gridy=7;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Statement st = DatabaseConnection.connectToDatabase("bank_system", "root", "password");

                passwordMatchesField.setVisible(true);
                passwordLengthField.setVisible(true);
                passwordLetterField.setVisible(true);
                passwordSpecialCharField.setVisible(true);
                passwordDigitField.setVisible(true);

                String password = new String(passwordField.getPassword());
                String repeated_password = new String(repeatPasswordField.getPassword());
                boolean password_is_valid=true;
                if(DataValidation.passwordMatches(password, repeated_password)){passwordMatchesField.setText("ok");}
                else{passwordMatchesField.setText("passwords dont match");password_is_valid=false;}
                if(DataValidation. passwordLength(password)){passwordLengthField.setText("ok");}
                else{passwordLengthField.setText("password too short");password_is_valid=false;}
                if(DataValidation.letterInPassword(password)){passwordLetterField.setText("ok");}
                else{passwordLetterField.setText("at least one upper or lowercase letter");password_is_valid=false;}
                if(DataValidation.digitInPassword(password)){passwordDigitField.setText("ok");}
                else{passwordDigitField.setText("at least one digit");password_is_valid=false;}
                if(DataValidation.specialCharInPassword(password)){passwordSpecialCharField.setText("ok");}
                else{passwordSpecialCharField.setText("at least one special character");password_is_valid=false;}

                if(password_is_valid){
                    user.changePassword(st, password);
                    JOptionPane.showMessageDialog(frame, "password successfully changed!");
                    frame.dispose();
                    prev_screen.prev_screen.prev_screen.user = user;
                    prev_screen.prev_screen.prev_screen.CreateScreen();
                }
            }
        });
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(returnButton,gbc);

        gbc.gridx=1;
        gbc.gridy=9;
        frame.add(exitButton,gbc);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
