package src.Settings;

import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class ChangeUsernameScreen extends Screen{
    public JPanel panel;
    public JTextField usernameField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel usernameLabel;
    public ChangeUsernameScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Statement st = Database.connectToDatabase("bank_system", "root", "password");
                String new_username = usernameField.getText();
                if(Database.isUsernameTaken(st, new_username)){
                    usernameField.setText("username is already taken");
                }
                else{
                    Database.setUsername(st, user.username, new_username);
                    JOptionPane.showMessageDialog(frame, "username successfully changed!");
                    user.username = new_username;
                    frame.dispose();
                    prev_screen.user = user;
                    prev_screen.CreateScreen();
                }
            };});
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
