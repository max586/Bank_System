package src.Settings;

import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeEmailScreen extends Screen{
    private JPanel panel;
    private JTextField emailField;
    private JButton submitButton;
    private JButton returnButton;
    private JButton exitButton;
    private JLabel emailLabel;
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
                    prev_screen.CreateScreen();
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
