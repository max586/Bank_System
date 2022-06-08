package src.Settings;

import src.AuthenticationAndRegistration.EmailVerificationScreen;
import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Statement;

public class ChangePasswordScreen1 extends Screen{
    public JPanel panel;
    public JButton forgotPasswordButton;
    public JLabel prevPasswordLabel;
    public JPasswordField prevPasswordField;
    public JLabel newPasswordLabel;
    public JPasswordField newPasswordField;
    public JLabel matchLabel;
    public JLabel lengthLabel;
    public JLabel letterLabel;
    public JLabel digitLabel;
    public JLabel specialCharLabel;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel timerLabel;
    public int counter=0;

    public ChangePasswordScreen1(){}
    public ChangePasswordScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                

                String prev_password = new String(prevPasswordField.getPassword());
                String new_password = new String(newPasswordField.getPassword());
                boolean password_is_valid=true;
                if(DataValidation.passwordMatches(user.password, prev_password)){matchLabel.setText("ok");}
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
                    Database.setPassword( user.username,new_password);
                    JOptionPane.showMessageDialog(frame, "password successfully changed!");
                    frame.dispose();
                    prev_screen.user = user;
                    prev_screen.CreateScreen();
                }
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                frame.dispose();
                new EmailVerificationScreen(user, ChangePasswordScreen1.this, new ChangePasswordScreen2()).CreateScreen();
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
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {counter=0;}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {counter=0;}
            @Override
            public void mouseEntered(MouseEvent e) {counter=0;}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        new Thread() {
            public void run() {
                while (counter <= 120) {
                    if(!frame.isDisplayable()){counter=0;}
                    else {
                        timerLabel.setText("Time before log out: " + (120 - counter++));
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
                frame.dispose();
            }
        }.start();
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
