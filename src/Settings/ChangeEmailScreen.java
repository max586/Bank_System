package src.Settings;

import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeEmailScreen extends Screen{
    public JPanel panel;
    public JTextField emailField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel emailLabel;
    public ChangeEmailScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                user.email = emailField.getText();
                frame.dispose();
                new EmailVerificationScreen(user, ChangeEmailScreen.this, prev_screen).CreateScreen();
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
}
