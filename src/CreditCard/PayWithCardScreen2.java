package src.CreditCard;

import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Statement;

public class PayWithCardScreen2 extends Screen{
    public JLabel balanceLabel;
    public JTextField paymentField;
    public JButton submitButton;
    public JButton exitButton;
    public JLabel amountLabel;
    public JPanel panel;
    public JLabel timerLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    public int counter=0;

    public PayWithCardScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jdialog=jpane.createDialog(panel,"");
        jdialog.setSize(300,150);
    }
    public void CreateScreen(){
        
        frame.setContentPane(panel);

        user.ordinary_account_balance = Database.getOrdinaryAccountBalance(st, user.username);
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
                    Database.setOrdinaryAccountBalance(st,user.username,user.ordinary_account_balance-payment);
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
        frame.setSize(400,300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PayWithCardScreen1(new User(),null,null).CreateScreen();
    }
}
