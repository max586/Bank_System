package src.CreditCard;

import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayWithCardScreen2 extends Screen{
    public JLabel balanceLabel;
    public JTextField paymentField;
    public JButton submitButton;
    public JButton exitButton;
    public JLabel amountLabel;
    public JPanel panel;
    public JLabel timeLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    public int counter=0;

    public PayWithCardScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jdialog=jpane.createDialog(panel,"");
        jdialog.setSize(450,200);
    }
    public void CreateScreen(){
        frame.setTitle("Pay with credit card second Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        user.ordinary_account_balance = Database.getOrdinaryAccountBalance( user.username);
        balanceLabel.setText("Current balance is: "+Float.toString(user.ordinary_account_balance));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                float payment = Float.parseFloat(paymentField.getText());
                if(payment>user.ordinary_account_balance) {
                    jpane.setMessage("Too little money on account to make a payment");
                    jpane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    jdialog.setTitle("Warning");
                    jdialog.setVisible(true);
                }
                else if(payment<0){
                    jpane.setMessage("Incorrect payment amount");
                    jpane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    jdialog.setTitle("Warning");
                    jdialog.setVisible(true);
                }
                else{
                    Database.setOrdinaryAccountBalance(user.username,user.ordinary_account_balance-payment);
                    jpane.setMessage("Payment successfully processed");
                    jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    jdialog.setTitle("Info");
                    jdialog.setVisible(true);
                    frame.dispose();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        frame.setSize(600,400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PayWithCardScreen1(new User(),null,null).CreateScreen();
    }
}
