package src.Settings;

import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class ChangePasswordScreen2 extends Screen{
    private JPanel panel;
    private JPasswordField prevPasswordField;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordField;
    private JLabel matchLabel;
    private JLabel lengthLabel;
    private JLabel letterLabel;
    private JLabel digitLabel;
    private JLabel specialCharLabel;
    private JLabel prevPasswordLabel;

    private JButton submitButton;
    private JButton ForgotPasswordButton;
    private JButton returnButton;
    private JButton exitButton;

    public ChangePasswordScreen2(){}
    public ChangePasswordScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        super.CreateScreen();
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Statement st = Database.connectToDatabase("bank_system", "root", "password");

                String new_password = new String(prevPasswordField.getPassword());
                String repeated_password = new String(newPasswordField.getPassword());
                boolean password_is_valid=true;
                if(DataValidation.passwordMatches(new_password, repeated_password)){matchLabel.setText("ok");}
                else{matchLabel.setText("passwords dont match");password_is_valid=false;}
                if(DataValidation. passwordLength(new_password)){lengthLabel.setText("ok");}
                else{lengthLabel.setText("password too short");password_is_valid=false;}
                if(DataValidation.letterInPassword(new_password)){letterLabel.setText("ok");}
                else{letterLabel.setText("at least one upper or lowercase letter");password_is_valid=false;}
                if(DataValidation.digitInPassword(new_password)){digitLabel.setText("ok");}
                else{digitLabel.setText("at least one digit");password_is_valid=false;}
                if(DataValidation.specialCharInPassword(new_password)){specialCharLabel.setText("ok");}
                else{specialCharLabel.setText("at least one special character");password_is_valid=false;}

                if(password_is_valid){
                    user.password=new_password;
                    Database.setPassword(st, user.username, new_password);
                    JOptionPane.showMessageDialog(frame, "password successfully changed!");
                    frame.dispose();
                    prev_screen.prev_screen.prev_screen.user = user;
                    prev_screen.prev_screen.prev_screen.CreateScreen();
                }
            }
        });
        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                if(prev_screen!=null){
                    prev_screen.CreateScreen();
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
