package src.Settings;

import src.DatabaseConnection;
import src.User;
import src.AuthenticationAndRegistration.DataValidation;
import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.Screen;

import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.Statement;

public class ChangePasswordScreen1 extends Screen{
    JLabel prevPasswordLabel;
    JPasswordField prevPasswordField;
    JLabel newPasswordLabel;
    JPasswordField newPasswordField;
    JLabel passwordMatchesField;
    JLabel passwordLengthField;
    JLabel passwordLetterField;
    JLabel passwordDigitField;
    JLabel passwordSpecialCharField;
    JButton submitButton;
    JButton forgotPasswordButton;

    public ChangePasswordScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Change password screen1");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        prevPasswordLabel = new JLabel("Enter previous password");
        frame.add(prevPasswordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        prevPasswordField = new JPasswordField();
        frame.add(prevPasswordField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        newPasswordLabel = new JLabel("Enter new password");
        frame.add(newPasswordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        newPasswordField = new JPasswordField();
        frame.add(newPasswordField,gbc);

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

                String prev_password = new String(prevPasswordField.getPassword());
                String new_password = new String(newPasswordField.getPassword());
                boolean password_is_valid=true;
                if(DataValidation.passwordMatches(user.password, prev_password)){passwordMatchesField.setText("ok");}
                else{passwordMatchesField.setText("passwords dont match");password_is_valid=false;}
                if(DataValidation. passwordLength(new_password)){passwordLengthField.setText("ok");}
                else{passwordLengthField.setText("password too short");password_is_valid=false;}
                if(DataValidation.letterInPassword(new_password)){passwordLetterField.setText("ok");}
                else{passwordLetterField.setText("at least one upper or lowercase letter");password_is_valid=false;}
                if(DataValidation.digitInPassword(new_password)){passwordDigitField.setText("ok");}
                else{passwordDigitField.setText("at least one digit");password_is_valid=false;}
                if(DataValidation.specialCharInPassword(new_password)){passwordSpecialCharField.setText("ok");}
                else{passwordSpecialCharField.setText("at least one special character");password_is_valid=false;}

                if(password_is_valid){
                    user.changePassword(st, new_password);
                    JOptionPane.showMessageDialog(frame, "password successfully changed!");
                    frame.dispose();
                    prev_screen.user = user;
                    prev_screen.CreateScreen();
                }
            }
        });
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridwidth=1;
        forgotPasswordButton = new JButton("Forgot your password?");
        forgotPasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                frame.dispose();
                new EmailVerificationScreen(user, ChangePasswordScreen1.this, new ChangePasswordScreen2()).CreateScreen();          
            }
        });
        frame.add(forgotPasswordButton, gbc);

        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(returnButton,gbc);

        gbc.gridx=1;
        gbc.gridy=10;
        frame.add(exitButton,gbc);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
