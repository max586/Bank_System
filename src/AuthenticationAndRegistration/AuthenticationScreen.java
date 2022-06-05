package src.AuthenticationAndRegistration;

import src.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class AuthenticationScreen extends Screen {
    private JTextField usernameField;
    private JButton signUpButton;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton exitButton;
    private JPanel panel;
    private JButton signInButton;

    public AuthenticationScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        
        frame.setContentPane(panel);
        signInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                user = new User();
                user.username=usernameField.getText();
                user.password=new String(passwordField.getPassword());
                Statement st = Database.connectToDatabase("bank_system", "root", "17391425");
                if(Database.verifyUser(st,user.username,user.password)){
                    JOptionPane.showMessageDialog(frame, "user successfully verified");
                    frame.dispose();
                    user.email = Database.getEmail(st, user.username);
                    user.ordinary_account_number = Database.getOrdinaryAccountNumber(st, user.username);
                    user.savings_account_number = Database.getSavingsAccountNumber(st, user.username);
                    if(next_screen!=null){
                        new ChooseAccountNumberScreen(user, AuthenticationScreen.this,new Screen()).CreateScreen();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "user doesn't exist");
                }
            }
        });
        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.dispose();
                new RegistrationScreen1(user, AuthenticationScreen.this, new Screen()).CreateScreen();
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
        new AuthenticationScreen(null,null,new Screen()).CreateScreen();
    }
}
