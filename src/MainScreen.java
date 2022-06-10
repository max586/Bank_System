package src;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Credits.Credit;
import src.Settings.SettingsMainScreen;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.transfers.AccountChoosed;
import src.transfers.TransferFactory;

import javax.swing.*;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.event.*;

public class MainScreen extends Screen {
    public JPanel AuthPanel;
    public JButton PROFILButton;
    public JButton incountryButton;
    public JButton KREDYTYButton;
    public JButton wylogujButton;
    public JButton prevButton;
    public JLabel timeLabel;
    public JLabel OrdAccNum;
    public JButton foreignTransferButton;
    public JButton ownTransferButton;
    public JButton standingOrderTransferButton;
    private JButton historyButton;
    private JLabel SavAccNum;
    public int counter = 0;
    String []options = {"one","two"};


    public MainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }

    public void CreateScreen(){

        frame.setSize(1080,720);
        frame.setTitle("MainScreen");
        AppTimer appTimer = new AppTimer(timeLabel,this);
        AuthPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();



            OrdAccNum.setText(Database.getOrdinaryAccountNumber(user.username));


            SavAccNum.setText(Database.getSavingsAccountNumber( user.username));


//Incountry Transfer, Foreign Transfer, Own Transfer, Standing Order Transfer, BLIK Phone Transfer
        foreignTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AccountChoosed.ORDINARYACCOUNT == AccountChoosed.ORDINARYACCOUNT) {
                    frame.dispose();
                    try {
                        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.ZAGRANICZNY);
                    } catch (Exception e2) {
                    }
                }
            }
        });

        incountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    try {
                        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.KRAJOWY);
                    } catch (Exception e2) {
                    }

            }
        });

        ownTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
;                try {
                    new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.WLASNY);
                }catch(Exception e2){}
            }
        });

        standingOrderTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    try {
                        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.ZLECENIESTALE);
                    } catch (Exception e2) {
                    }

            }
        });
        KREDYTYButton.addActionListener(e->
        {
            frame.dispose();
                new Credit(user, MainScreen.this, new Screen()).CreateScreen();
            });
        PROFILButton.addActionListener(e->
        {
            frame.dispose();
            new SettingsMainScreen(user, MainScreen.this, new Screen()).CreateScreen();
        });

        wylogujButton.addActionListener(e->
        {
            frame.dispose();
            new AuthenticationScreen(null,null,new Screen()).CreateScreen();
        });

        prevButton.addActionListener(e->
        {
            frame.dispose();
            if(prev_screen!=null){
                prev_screen.frame.setVisible(true);
        }

        });
        frame.setContentPane(AuthPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        User test_user = new User();
        test_user.username="test_user";
        test_user.appCode="1234";
        test_user.savings_account_number="PL999";
        test_user.ordinary_account_balance= (float) 86751.33;
        test_user.savings_account_balance=10000;
        test_user.ordinary_account_number="PL666";
        new MainScreen(test_user,null,null).CreateScreen();
    }
}

