package src.CreditCard;

import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class PayWithCardScreen2 extends Screen{
    private JLabel balanceLabel;
    private JTextField paymentField;
    private JButton submitButton;
    private JButton exitButton;
    private JLabel amountLabel;
    private JPanel panel;

    public PayWithCardScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen(){
        super.CreateScreen();
        frame.setContentPane(panel);

        Statement st = Database.connectToDatabase("bank_system","root","password");
        user.ordinary_account_balance = Database.getOrdinaryAccountBalance(st, user.username);
        balanceLabel.setText("Current balance is: "+Float.toString(user.ordinary_account_balance));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                float payment = Float.parseFloat(paymentField.getText());
                if(payment>user.ordinary_account_balance){
                   JOptionPane.showMessageDialog(frame,"Too little money on account to make a payment");
                }
                else{
                    Database.setOrdinaryAccountBalance(st,user.username,user.ordinary_account_balance-payment);
                    JOptionPane.showMessageDialog(frame,"payment successfully processed");
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

        frame.setSize(400,300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PayWithCardScreen1(new User(),null,null).CreateScreen();
    }
}
