package src.Settings;

import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChangeEmailScreen extends Screen{
    public JPanel panel;
    public JTextField emailField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel emailLabel;
    public JLabel timeLabel;
    public int counter=0;

    public ChangeEmailScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        frame.setTitle("Change email Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

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
