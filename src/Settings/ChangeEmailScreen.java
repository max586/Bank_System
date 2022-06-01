package src.Settings;

import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.User;
import src.Screen;
import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;

public class ChangeEmailScreen extends Screen{
    JLabel emailLabel;
    JTextField emailField;
    JButton submitButton;

    public ChangeEmailScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Change email screen");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        emailLabel = new JLabel("Enter new email address");
        frame.add(emailLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        emailField = new JTextField();
        frame.add(emailField,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                user.email = emailField.getText();
                frame.dispose();
                new EmailVerificationScreen(user, ChangeEmailScreen.this, prev_screen).CreateScreen();
            }
        });
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(returnButton,gbc);

        gbc.gridx=1;
        gbc.gridy=3;
        frame.add(exitButton,gbc);
        
        frame.pack();
        frame.setVisible(true);
    }
}

