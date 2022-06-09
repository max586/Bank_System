package src.CreditCard;
import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayWithCardScreen1 extends Screen {
    public JTextField nrField;
    public JTextField pinField;
    public JLabel nrLabel;
    public JLabel pinLabel;
    public JPanel panel;
    public JButton submitButton;
    public JButton exitButton;
    public JLabel descrLabel;
    public JLabel timeLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    public int counter=0;

    public PayWithCardScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jdialog=jpane.createDialog(panel,"");
    }
    @Override
    public void CreateScreen(){
        frame.setTitle("Pay with credit card first Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nr = nrField.getText();
                String pin = pinField.getText();
                if(Database.verifyCard(nr,pin)){
                    user.card_number = nr;
                    user.pin_code = pin;
                    user.username = Database.getUsernameByCard(user.card_number);
                    jpane.setMessage("card successfully verified");
                    jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    jdialog.setTitle("Info");
                    jdialog.setVisible(true);
                    frame.dispose();
                    if(next_screen!=null) {
                        new PayWithCardScreen2(user, null, null).CreateScreen();
                    }
                }
                else{
                    jpane.setMessage("wrong card number or pin");
                    jpane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    jdialog.setTitle("Warning");
                    jdialog.setVisible(true);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        frame.setSize(400,300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        User test_user = new User();
        test_user.username="test_user";
        new PayWithCardScreen1(test_user,null,null).CreateScreen();
    }
}
