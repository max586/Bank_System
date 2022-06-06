package src.CreditCard;
import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class PayWithCardScreen1 extends Screen {
    public JTextField nrField;
    public JTextField pinField;
    public JLabel nrLabel;
    public JLabel pinLabel;
    public JPanel panel;
    public JButton submitButton;
    public JButton exitButton;
    public JLabel descrLabel;

    public PayWithCardScreen1(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Statement st = Database.connectToDatabase("bank_system","root","password");
                String nr = nrField.getText();
                String pin = pinField.getText();
                if(Database.verifyCard(st,nr,pin)){
                    user.card_number = nr;
                    user.pin_code = pin;
                    user.username = Database.getUsernameByCard(st,user.card_number);
                    frame.dispose();
                    new PayWithCardScreen2(user,null,null).CreateScreen();
                }
                else{
                    JOptionPane.showMessageDialog(frame,"wrong card number or pin","Alert",JOptionPane.WARNING_MESSAGE);
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
