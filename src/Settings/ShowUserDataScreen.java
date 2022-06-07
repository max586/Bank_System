package src.Settings;

import src.Screen;
import src.User;

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
    public JTextField addressField;
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
    public JLabel addressLabel;
    public JLabel ordinaryLabel;
    public JLabel savingsLabel;
    public JLabel cardLabel;
    public JLabel pinLabel;
    public JLabel lastNameLabel;
    public JPanel panel;
    public JLabel timerLabel;
    private JLabel peselLabel;
    public int counter=0;
    public ShowUserDataScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        frame.setContentPane(panel);

        usernameField.setText(user.username);
        passwordField.setText(user.password);
        emailField.setText(user.email);
        firstNameField.setText(user.firstName);
        lastNameField.setText(user.lastName);
        sexField.setText(user.sex);
        cityField.setText(user.city);
        addressField.setText(user.address);
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
        frame.setSize(1000,800);
        frame.setVisible(true);
    }
}
