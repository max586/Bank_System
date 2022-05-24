package src.AuthenticationAndRegistration;

import src.Screen;
import src.User;
import src.DatabaseConnection;
import src.MainScreen;
import javax.swing.*;
import java.sql.Statement;
import java.awt.event.*;
import java.awt.Insets;

public class AuthenticationScreen extends Screen{
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton signInButton;
    JButton signUpButton;

    public AuthenticationScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }

    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Authentication screen");
        
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.insets = new Insets(0,5,5,10);
        usernameLabel = new JLabel("login");
        frame.add(usernameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.insets = new Insets(0,10,5,5);
        usernameField = new JTextField();
        frame.add(usernameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,10,10);
        passwordLabel = new JLabel("password");
        frame.add(passwordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,10,10,5);
        passwordField = new JPasswordField();
        frame.add(passwordField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.insets = new Insets(10,5,0,5);
        signInButton = new JButton("Sign in");
        signInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                user = new User();
                user.username=usernameField.getText();
                user.password=new String(passwordField.getPassword());
                Statement st = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
                    if(user.verifyUser(st)){
                        JOptionPane.showMessageDialog(frame, "user successfully verified");
                        frame.dispose();
                        user.getUserAccounts(st);
                        if(next_screen!=null){
                            if(user.ordinary_account_number==null || user.savings_account_number==null){
                                new MainScreen(user, prev_screen, new Screen()).CreateScreen();
                            }
                            else{
                                new ChooseAccountNumberScreen(user,AuthenticationScreen.this,new Screen()).CreateScreen();
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "user doesn't exist");
                    }
            }
        });
        frame.add(signInButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth=1;
        gbc.insets = new Insets(0,5,0,5);
        signUpButton = new JButton("sign up");
        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.dispose();
                new RegistrationScreen1(user, AuthenticationScreen.this, new Screen()).CreateScreen();

            }
        });
        frame.add(signUpButton,gbc);

        //return button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth=1;
        gbc.insets = new Insets(0,5,5,5);
        frame.add(returnButton, gbc);

        //exit button
        gbc.gridx = 1;
        gbc.gridy = 5;
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {  
        AuthenticationScreen screen = new AuthenticationScreen(null,null,null);
        screen.CreateScreen();
    }  
}  

