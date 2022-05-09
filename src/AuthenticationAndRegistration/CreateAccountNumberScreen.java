package src.AuthenticationAndRegistration;

import src.*;
import javax.swing.*;
import java.sql.Statement;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Random;
import java.time.LocalDateTime;

public class CreateAccountNumberScreen {
    static JLabel descr;
    static JLabel ordinaryAccountDescr;
    static JRadioButton ordinaryAccountChoice;
    static JLabel savingsAccountDescr;
    static JRadioButton savingsAccountChoice;
    static JButton createAccountButton;
    static JButton exitButton;
    
    public static void CreateScreen(User user){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Create account number screen");

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        descr = new JLabel("Choose account type you want to create");
        gbc.insets = new Insets(5,5,5,5);
        frame.add(descr,gbc);
    
        ordinaryAccountChoice = new JRadioButton("Ordinary account");
        savingsAccountChoice = new JRadioButton("Savings account");
        ButtonGroup group = new ButtonGroup();
        group.add(ordinaryAccountChoice);
        group.add(savingsAccountChoice);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,10);     
        frame.add(ordinaryAccountChoice,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        frame.add(savingsAccountChoice,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        ordinaryAccountDescr = new JLabel("<html>Ordinary Account:<br>"+
        "-free cash deposit and withdrawal;<br>"+
        "-free national and internal transactions;<br>"+
        "-standard credit card for free, or with personal outlook for 15zl;<br> </html>");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(ordinaryAccountDescr,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        savingsAccountDescr = new JLabel("<html>Savings Account:<br>"+
        "-free cash deposit and withdrawal;<br>"+
        "-free internal transactions;<br>"+
        "-1.0% interest;</html>");
        gbc.insets = new Insets(5,10,5,5);
        frame.add(savingsAccountDescr,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=2;
        createAccountButton = new JButton("Create account");
        Connection con = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
        try{
            Statement st = con.createStatement();
            createAccountButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if(ordinaryAccountChoice.isSelected()){
                        user.ordinary_account_number = generateAccountNumber();
                        user.addOrdinaryAccountNumber(st);
                        JOptionPane.showMessageDialog(frame, "Account successfully created!\nYour personal account number:\n"+user.ordinary_account_number);
                        frame.dispose();
                        AuthenticationScreen.CreateScreen();
                    }
                    else if(savingsAccountChoice.isSelected()){
                        user.savings_account_number = generateAccountNumber();
                        user.addSavingsAccountNumber(st);
                        JOptionPane.showMessageDialog(frame, "Account successfully created!\nYour personal account number:\n"+user.savings_account_number);
                        frame.dispose();
                        AuthenticationScreen.CreateScreen();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "account type wasn't chosen!");
                    }               
                }
        });}
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        gbc.insets = new Insets(5,5,5,5);
        frame.add(createAccountButton,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=2;
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                        frame.dispose();  
                    }  
        }); 
        gbc.insets = new Insets(5,5,5,5);
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }

    static String generateAccountNumber(){
        String account_number="1100";
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
        account_number=Integer.toString((checksum-checksum%10)/10)+Integer.toString(checksum%10)+account_number;
        return account_number;
    }

    public static void main(String[] args) {
        User test_user = new User();
        test_user.username="test_user";
        test_user.password="password";
        CreateScreen(test_user);        
    }    
}
