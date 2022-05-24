package src;

import javax.swing.*;
import src.Settings.SettingsMainScreen;
import java.awt.event.*;
import java.awt.Insets;

public class MainScreen extends Screen{
    JButton settingsButton;
    public MainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Main screen");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            if(next_screen!=null){
                frame.dispose();
                new SettingsMainScreen(user,MainScreen.this, new Screen()).CreateScreen();
            }
        }
        });
        frame.add(settingsButton, gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        frame.add(returnButton,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
}
