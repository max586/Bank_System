package src.Settings;

import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsMainScreen extends Screen {
    public JPanel panel;
    public JButton showUserDataButton;
    public JButton logOutButton;
    public JButton changeUsernameButton;
    public JButton changePasswordButton;
    public JButton changeEmailButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel timeLabel;
    public int counter=0;

    public SettingsMainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        frame.setTitle("Settings main Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

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
        showUserDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(next_screen!=null){
                    new ShowUserDataScreen(user,SettingsMainScreen.this, new Screen()).CreateScreen();
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
        test_user.firstName="fn";
        test_user.lastName="ln";
        test_user.sex="M";
        test_user.city="somewhere";
        //test_user.address="ul x, y";
        test_user.pesel="666";
        test_user.ordinary_account_number="PL666";
        test_user.savings_account_number="PL999";
        test_user.card_number="123456";
        test_user.pin_code="1234";
        new SettingsMainScreen(test_user,null,new Screen()).CreateScreen();
    }
}
