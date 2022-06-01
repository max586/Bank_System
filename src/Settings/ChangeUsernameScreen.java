package src.Settings;

import src.Database;
import src.User;
import src.Screen;
import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.Statement;

public class ChangeUsernameScreen extends Screen{
    JLabel usernameLabel;
    JTextField usernameField;
    JButton submitButton;

    public ChangeUsernameScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Change username screen");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        usernameLabel = new JLabel("Username");
        frame.add(usernameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,10);
        usernameField = new JTextField();
        frame.add(usernameField,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,5);
        submitButton = new JButton("Submit");
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
        frame.add(submitButton, gbc);

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
    public static void main(String[] args) {
        //CreateScreen(null, null);
    }
}
