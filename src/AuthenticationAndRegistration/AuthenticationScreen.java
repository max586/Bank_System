package src.AuthenticationAndRegistration;

import src.*;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.*;
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
    public JLabel timeLabel;
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
        frame.setTitle("Authentication Screen");
        frame.setContentPane(panel);

        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        signInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                user = new User();
                user.username=usernameField.getText();
                user.password=new String(passwordField.getPassword());

                if(Database.verifyUser(user.username,user.password)){
                    jpane.setMessage("user successfully verified");
                    jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    jdialog.setTitle("Info");
                    jdialog.setVisible(true);
                    frame.dispose();
                    user.email = Database.getEmail( user.username);
                    user.appCode=Database.getAppCode(user.username);
                    String[] user_data = Database.getUserData(user.username);
                    user.firstName=user_data[0];
                    user.lastName=user_data[1];
                    user.sex=user_data[2];
                    user.phone_number=user_data[3];
                    user.city=user_data[4];
                    user.post_code=user_data[5];
                    user.street=user_data[6];
                    user.street_nr=user_data[7];
                    user.pesel=user_data[8];
                    user.ordinary_account_number = Database.getOrdinaryAccountNumber( user.username);
                    user.ordinary_account_balance=Database.getOrdinaryAccountBalance(user.username);
                    user.savings_account_number = Database.getSavingsAccountNumber( user.username);
                    user.savings_account_balance=Database.getSavingsAccountBalance(user.username);
                    String[] card = Database.getCard(user.username);
                    user.card_number=card[0];
                    user.pin_code=card[1];

                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, AuthenticationScreen.this,new Screen()).CreateScreen();
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
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AuthenticationScreen(null,null,new Screen()).CreateScreen();
    }
}
