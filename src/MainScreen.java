package src;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Credits.Credit;
import src.Settings.SettingsMainScreen;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.transfers.AccountChoosed;
import src.transfers.OrdinaryHistory;
import src.transfers.SavingsHistory;
import src.transfers.TransferFactory;

import javax.swing.*;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.event.*;
import java.sql.SQLException;

public class MainScreen extends Screen {
    public JPanel AuthPanel;
    public JButton settingsButton;
    public JButton domesticButton;
    public JButton creditButton;
    public JButton logoutButton;
    public JLabel AccNumber;
    public JLabel OrdAccNum;
    public JLabel SavAccNum;

    public JLabel AccType;
    public JButton foreignTransferButton;
    public JButton ownTransferButton;
    public JButton standingOrderTransferButton;
    private JButton ordinaryHistoryButton;
    private JButton savingsHistoryButton;
    private JLabel panelTitleLabel;
    private JButton FAQButton;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JButton blikButton;
    private JLabel OrdBalance;
    private JLabel SavBalance;
    public int counter = 0;
    AccountChoosed chosenAcc;


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
            OrdBalance.setText(String.valueOf(Database.getOrdinaryAccountBalance(user.username)));

            SavAccNum.setText(Database.getSavingsAccountNumber( user.username));
            SavBalance.setText(String.valueOf(Database.getSavingsAccountBalance(user.username)));

        FAQButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new FAQScreen(user,new MainFrame());
            }
        });
        foreignTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    try {
                        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.ZAGRANICZNY);
                    } catch (Exception e2) {
                    }

            }
        });
        domesticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    try {
                        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.KRAJOWY);
                    } catch (Exception e2) {
                }
            }
        });
        blikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new TransferFactory(AccountChoosed.ORDINARYACCOUNT, user, new MainFrame()).getTransfer(TransferFactory.TransferType.TELEFONBLIK);
                } catch (Exception e2) {

                }
            }
        });
        ordinaryHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new OrdinaryHistory(new MainFrame(),user);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        savingsHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new SavingsHistory(new MainFrame(),user);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        ownTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
;                try {
                    new TransferFactory(chosenAcc, user, new MainFrame()).getTransfer(TransferFactory.TransferType.WLASNY);
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
        creditButton.addActionListener(e->
        {
            frame.dispose();
                new Credit(user, MainScreen.this, new Screen()).CreateScreen();
            });
        settingsButton.addActionListener(e->
        {
            frame.dispose();
            new SettingsMainScreen(user, MainScreen.this, new Screen()).CreateScreen();
        });

        logoutButton.addActionListener(e->
        {
            frame.dispose();
            new AuthenticationScreen(null,null,new Screen()).CreateScreen();
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

