package src.Settings;

import src.Screen;
import src.User;
import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;

public class SettingsMainScreen extends Screen{
    JButton logOutButton;
    JButton changeUsernameButton;
    JButton changePasswordButton;
    JButton changeEmailButton;
    JButton changePersonalDataButton;
    JButton changeWithdrawalLimitButton;
    
    public SettingsMainScreen(){}
    public SettingsMainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Settings main screen");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        changeUsernameButton = new JButton("Change username");
        changeUsernameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();              
                if(next_screen!=null){
                    new ChangeUsernameScreen(user, SettingsMainScreen.this, new Screen()).CreateScreen();
                }
            }
        });
        frame.add(changeUsernameButton,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        changePasswordButton = new JButton("Change password");
        changePasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(next_screen!=null){
                    new ChangePasswordScreen1(user, SettingsMainScreen.this, new Screen()).CreateScreen(); 
                }
            }
        });
        frame.add(changePasswordButton,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        changeEmailButton = new JButton("Change email");
        changeEmailButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(next_screen!=null){
                    new ChangeEmailScreen(user, SettingsMainScreen.this, new Screen()).CreateScreen(); 
                }
            }
        });
        frame.add(changeEmailButton,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        frame.add(returnButton,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        frame.add(exitButton,gbc);

        frame.pack(); 
        frame.setVisible(true);       
    }
    public static void main(String[] args) {
        User test_user  = new User();
        test_user.username="test_user";
        test_user.password="password";
        test_user.email = "maks.ovsienko2@gmail.com";
        test_user.firstName="fn";
        test_user.lastName = "ln";
        test_user.sex="M";
        test_user.pesel="666";
        test_user.ordinary_account_number="PL666";
        test_user.savings_account_number="PL999";
        test_user.city="somewhere";
        test_user.address="ul x, y";
        new SettingsMainScreen(test_user, null, new Screen()).CreateScreen();
    }
}
