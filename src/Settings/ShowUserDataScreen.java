package src.Settings;

import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowUserDataScreen extends Screen{
    public JLabel usernameLabel;
    public JTextField usernameField;
    public JTextField emailField;
    public JTextField passwordField;
    public JLabel emailLabel;
    public JLabel passwordLabel;
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField sexField;
    public JTextField cityField;
    public JTextField streetField;
    public JTextField peselField;
    public JTextField ordinaryField;
    public JTextField savingsField;
    public JTextField cardField;
    public JTextField pinField;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel firstNameLabel;
    public JLabel cityLabel;
    public JLabel sexLabel;
    public JLabel streetLabel;
    public JLabel ordinaryLabel;
    public JLabel savingsLabel;
    public JLabel cardLabel;
    public JLabel pinLabel;
    public JLabel lastNameLabel;
    public JPanel panel;
    public JLabel timeLabel;
    public JLabel peselLabel;
    public JLabel phoneNrLabel;
    public JTextField phoneNrField;
    public JLabel streetNrLabel;
    public JTextField streetNrField;
    public JLabel postCodeLabel;
    public JTextField postCodeField;
    public JLabel appCodeLabel;
    public JTextField appCodeField;
    public int counter=0;
    public ShowUserDataScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        frame.setTitle("Show user data Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        usernameField.setText(user.username);
        passwordField.setText(user.password);
        emailField.setText(user.email);
        appCodeField.setText(user.appCode);
        firstNameField.setText(user.firstName);
        lastNameField.setText(user.lastName);
        sexField.setText(user.sex);
        cityField.setText(user.city);
        phoneNrField.setText(user.phone_number);
        postCodeField.setText(user.post_code);
        streetField.setText(user.street);
        streetNrField.setText(user.street_nr);
        peselField.setText(user.pesel);
        ordinaryField.setText(user.ordinary_account_number);
        savingsField.setText(user.savings_account_number);
        cardField.setText(user.card_number);
        pinField.setText(user.pin_code);

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
        frame.setSize(1000,800);
        frame.setVisible(true);
    }
}
