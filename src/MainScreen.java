package src;

import src.Settings.SettingsMainScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.Random;

public class MainScreen extends Screen{
    public JPanel panel;
    public JButton settingsButton;
    public JButton returnButton;
    public JButton exitButton;
    public MainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {

        frame.setContentPane(panel);

        settingsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(next_screen!=null){
                    frame.dispose();
                    new SettingsMainScreen(user, MainScreen.this, new Screen()).CreateScreen();
                }
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
        new MainScreen(test_user,null,null).CreateScreen();
    }
}
