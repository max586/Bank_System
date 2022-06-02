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

public class CreatedAccountAndCardNumberScreen extends Screen {
    private JTextField ordinaryAccountField;
    private JTextField savingsAccountField;
    private JLabel ordinaryAccountLabel;
    private JButton OKButton;
    private JButton returnButton;
    private JButton exitButton;
    private JPanel panel;
    private JTextField cardNumberField;
    private JTextField pinField;
    private JLabel cardDescrLabel;
    private JLabel accountsDescrLabel;
    private JLabel pinLabel;
    private JLabel cardLabel;
    private JLabel ordinaryAccountdescrLabel;
    private JLabel savingsAccountdescrLabel;
    public CreatedAccountAndCardNumberScreen(User user, Screen prev_screen, Screen next_screen){super(user,prev_screen,next_screen);}

    public void CreateScreen(){
        super.CreateScreen();
        frame.setContentPane(panel);

        String ordinary_account_number = generateAccountNumber();
        String savings_account_number = generateAccountNumber();
        String card_number = generateCardNumber();
        String pin_code = generatePIN();

        Statement st = Database.connectToDatabase("bank_system","root","password");
        Database.addOrdinaryAccountNumber(st, user.username, ordinary_account_number);
        Database.addSavingsAccountNumber(st, user.username, savings_account_number);
        Database.addCard(st, user.username, card_number, pin_code);

        user.ordinary_account_number = ordinary_account_number;
        user.savings_account_number = savings_account_number;
        user.card_number = card_number;
        user.pin_code = pin_code;

        ordinaryAccountField.setText(user.ordinary_account_number);
        savingsAccountField.setText(user.savings_account_number);
        cardNumberField.setText(user.card_number);
        pinField.setText(user.pin_code);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, "Welcome to our bank!");
                frame.dispose();
                new AuthenticationScreen(null,null,new Screen()).CreateScreen();
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

    public String generateAccountNumber(){
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
    public String generateCardNumber(){
        String nr="";
        Random rnd = new Random();
        for(int i=0;i<16;i++){nr+=Integer.toString(rnd.nextInt(10));}
        return nr;
    }
    public String generatePIN(){
        String pin="";
        Random rnd = new Random();
        for(int i=0;i<4;i++){pin+=Integer.toString(rnd.nextInt(10));}
        return pin;
    }

    public static void main(String[] args) {

    }
}
