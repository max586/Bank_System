package src.AuthenticationAndRegistration;

import src.Database;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Random;

public class CreateAccountNumberScreen extends Screen {
    private JPanel panel;
    private JRadioButton ordinaryAccountRadioButton;
    private JRadioButton savingsAccountRadioButton;
    private JLabel descr1Label;
    private JLabel descr2Label;
    private JLabel descr3Label;
    private JButton createAccountButton;
    private JButton returnButton;
    private JButton exitButton;

    public CreateAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        super.CreateScreen();
        frame.setContentPane(panel);

        Statement st = Database.connectToDatabase("bank_system", "root", "password");
        createAccountButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(ordinaryAccountRadioButton.isSelected()){
                    user.ordinary_account_number = generateAccountNumber();
                    Database.addOrdinaryAccountNumber(st, user.username, user.ordinary_account_number);
                    JOptionPane.showMessageDialog(frame, "Account successfully created!\nYour personal account number:\n"+user.ordinary_account_number);
                    frame.dispose();
                    if(next_screen != null){
                        new AuthenticationScreen(user, CreateAccountNumberScreen.this, new Screen()).CreateScreen();
                    }
                }
                //warning! duplicated code!!!
                else if(savingsAccountRadioButton.isSelected()){
                    user.savings_account_number = generateAccountNumber();
                    Database.addSavingsAccountNumber(st,user.username,user.savings_account_number);
                    JOptionPane.showMessageDialog(frame, "Account successfully created!\nYour personal account number:\n"+user.savings_account_number);
                    frame.dispose();
                    if(next_screen!=null){
                        new AuthenticationScreen(user, CreateAccountNumberScreen.this, new Screen()).CreateScreen();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "account type wasn't chosen!");
                }
            }
        });
        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                if(prev_screen!=null){
                    prev_screen.CreateScreen();
                }
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
    static String generateAccountNumber(){
        String account_number="1137";
        String date = LocalDateTime.now().toString().substring(0,10);
        date=date.replace("-", "");
        account_number+=date;
        Random rnd = new Random();
        for(int i=0;i<12;i++){
            account_number+=Integer.toString(rnd.nextInt(10));
        }

        //checksum
        int[] account_number_arr = new int[24];
        for(int i=0;i<24;i++){account_number_arr[i]=Integer.parseInt(String.valueOf(account_number.charAt(i)));}

        int checksum=0, coef=1;
        for(int i=0;i<24;i++){
            checksum+=account_number_arr[i]*coef;
            if(coef==1){coef=3;}
            if(coef==3){coef=7;}
            if(coef==7){coef=1;}
        }
        checksum=checksum%100;
        account_number="PL"+Integer.toString((checksum-checksum%10)/10)+Integer.toString(checksum%10)+account_number;
        return account_number;
    }
    public static void main(String[] args) {
        User test_user = new User();
        test_user.username="_user";
        test_user.password="password";
        new CreateAccountNumberScreen(test_user, null, null).CreateScreen();
    }
}
