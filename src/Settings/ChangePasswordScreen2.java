package src.Settings;

import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChangePasswordScreen2 extends Screen{
    public JPanel panel;
    public JLabel newPasswordLabel;
    public JPasswordField newPasswordField;
    public JLabel repeatedPasswordLabel;
    public JPasswordField repeatedPasswordField;
    public JLabel matchLabel;
    public JLabel lengthLabel;
    public JLabel letterLabel;
    public JLabel digitLabel;
    public JLabel specialCharLabel;
    public JLabel prevPasswordLabel;

    public JButton submitButton;
    public JButton ForgotPasswordButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel timeLabel;
    public int counter=0;

    public ChangePasswordScreen2(){}
    public ChangePasswordScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        repeatedPasswordField.setTransferHandler(null);
        frame.setTitle("Change password second screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                

                String new_password = new String(newPasswordField.getPassword());
                String repeated_password = new String(repeatedPasswordField.getPassword());
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
                    Database.setPassword( user.username, new_password);
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
