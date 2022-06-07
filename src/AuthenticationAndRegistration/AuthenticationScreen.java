package src.AuthenticationAndRegistration;

import src.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Statement;

public class AuthenticationScreen extends Screen {
    public JTextField usernameField;
    public JButton signUpButton;
    public JPasswordField passwordField;
    public JLabel usernameLabel;
    public JLabel passwordLabel;
    public JButton exitButton;
    public JPanel panel;
    public JButton signInButton;
    public JLabel timerLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    public int counter=0;

    public AuthenticationScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jdialog=jpane.createDialog(panel,"");
    }
    @Override
    public void CreateScreen(){
        
        frame.setContentPane(panel);
        signInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                user = new User();
                user.username=usernameField.getText();
                user.password=new String(passwordField.getPassword());
                Statement st = Database.connectToDatabase("bank_system", "root", "password");

                if(Database.verifyUser(st,user.username,user.password)){
                    jpane.setMessage("user successfully verified");
                    jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    jdialog.setTitle("Info");
                    jdialog.setVisible(true);
                    frame.dispose();
                    user.email = Database.getEmail(st, user.username);
                    String[] user_data = Database.getUserData(st,user.username);
                    user.firstName=user_data[0];
                    user.lastName=user_data[1];
                    user.sex=user_data[2];
                    user.city=user_data[3];
                    user.address=user_data[4];
                    user.pesel=user_data[5];
                    user.ordinary_account_number = Database.getOrdinaryAccountNumber(st, user.username);
                    user.savings_account_number = Database.getSavingsAccountNumber(st, user.username);
                    String[] card = Database.getCard(st,user.username);
                    user.card_number=card[0];
                    user.pin_code=card[1];
                    if(next_screen!=null){
                        new ChooseAccountNumberScreen(user, AuthenticationScreen.this,new Screen()).CreateScreen();
                    }
                }
                else{
                    jpane.setMessage("user doesn't exist");
                    jpane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    jdialog.setTitle("Warning");
                    jdialog.setVisible(true);
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
        frame.setSize(800,600);
        frame.setVisible(true);
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
    }

    public static void main(String[] args) {
        new AuthenticationScreen(null,null,new Screen()).CreateScreen();
    }
}
