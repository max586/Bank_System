package src.AuthenticationAndRegistration;

import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Random;

public class CreatedAccountAndCardNumberScreen extends Screen {
    public JTextField ordinaryAccountField;
    public JTextField savingsAccountField;
    public JLabel ordinaryAccountLabel;
    public JButton OKButton;
    public JButton returnButton;
    public JButton exitButton;
    public JPanel panel;
    public JTextField cardNumberField;
    public JTextField pinField;
    public JLabel cardDescrLabel;
    public JLabel accountsDescrLabel;
    public JLabel pinLabel;
    public JLabel cardLabel;
    public JLabel ordinaryAccountdescrLabel;
    public JLabel savingsAccountdescrLabel;
    public JLabel timeLabel;
    private JTextField appCodeField;
    private JLabel appCodeLabel;
    public int counter=0;

    public CreatedAccountAndCardNumberScreen(User user, Screen prev_screen, Screen next_screen){super(user,prev_screen,next_screen);}

    public void CreateScreen(){
        frame.setTitle("Account and card number Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        String ordinary_account_number = generateAccountNumber();
        String savings_account_number = generateAccountNumber();
        String card_number = generateCardNumber();
        String pin_code = generatePIN();

        Database.addOrdinaryAccountNumber( user.username, ordinary_account_number);
        Database.addSavingsAccountNumber( user.username, savings_account_number);
        Database.addCard( user.username, card_number, pin_code);

        user.ordinary_account_number = ordinary_account_number;
        user.savings_account_number = savings_account_number;
        user.card_number = card_number;
        user.pin_code = pin_code;

        ordinaryAccountField.setText(user.ordinary_account_number);
        savingsAccountField.setText(user.savings_account_number);
        cardNumberField.setText(user.card_number);
        pinField.setText(user.pin_code);
        appCodeField.setText(user.appCode);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(frame, "Welcome to our bank!");
                frame.dispose();
                appTimer.stop();
                new AuthenticationScreen(null,null,new Screen()).CreateScreen();
            }
        });

        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                appTimer.stop();
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
        frame.setSize(1000,800);
        frame.setVisible(true);
    }

    public static String generateAccountNumber(){
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
    public static String generateCardNumber(){
        String nr="";
        Random rnd = new Random();
        for(int i=0;i<16;i++){nr+=Integer.toString(rnd.nextInt(10));}
        return nr;
    }
    public static String generatePIN(){
        String pin="";
        Random rnd = new Random();
        for(int i=0;i<4;i++){pin+=Integer.toString(rnd.nextInt(10));}
        return pin;
    }

    public static void main(String[] args) {
        User test_user = new User();
        test_user.username="test_user";
        new CreatedAccountAndCardNumberScreen(test_user,null,new Screen()).CreateScreen();
        for(int i=0;i<10;i++){
            System.out.println(generateAccountNumber());
        }
    }

}
