package src.Settings;

import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMainScreen extends Screen {
    private JPanel panel;
    private JButton showUserDataButton;
    private JButton logOutButton;
    private JButton changeUsernameButton;
    private JButton changePasswordButton;
    private JButton changeEmailButton;
    private JButton returnButton;
    private JButton exitButton;

    public SettingsMainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        
        frame.setContentPane(panel);

        changeUsernameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(next_screen!=null){
                    new ChangeUsernameScreen(user, SettingsMainScreen.this, new Screen()).CreateScreen();
                }
            }
        });
        changePasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                changePasswordButton.resetKeyboardActions();
                if(next_screen!=null){
                    new ChangePasswordScreen1(user, SettingsMainScreen.this, new Screen()).CreateScreen();
                }
            }
        });
        changeEmailButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(next_screen!=null){
                    new ChangeEmailScreen(user, SettingsMainScreen.this, new Screen()).CreateScreen();
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                new AuthenticationScreen(null,null,new Screen()).CreateScreen();
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
        User test_user = new User();
        test_user.username="test_user";
        test_user.password="password";
        test_user.email="maks.ovsienko2@gmail.com";
        new SettingsMainScreen(test_user,null,new Screen()).CreateScreen();
    }
}
