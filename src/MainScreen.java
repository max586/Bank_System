package src;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Credits.Credit;
import src.Settings.SettingsMainScreen;
import src.mainFrame.MainFrame;
import src.transfers.AccountChoosed;
import src.transfers.OrdinaryHistory;
import src.transfers.SavingsHistory;
import src.transfers.TransferFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class MainScreen extends Screen {
    public JPanel AuthPanel;
    public JButton PROFILButton;
    public JButton incountryButton;
    public JButton KREDYTYButton;
    public JButton wylogujButton;
    public JButton prevButton;
    public JLabel timeCounter;
    public JLabel AccNumber;
    public JLabel AccType;
    public JButton foreignTransferButton;
    public JButton ownTransferButton;
    public JButton standingOrderTransferButton;
    private JButton savingsButton;
    private JButton BLIKTransferButton;
    private JButton ordinaryButton;
    public int counter = 0;
    String chosenAcc;
    String []options = {"one","two"};


    public MainScreen(User user, Screen prev_screen, Screen next_screen, String option){
        super(user,prev_screen,next_screen);
        chosenAcc = option;
    }

    public void CreateScreen(){

        frame = new JFrame();
        frame.setSize(1080,720);
        frame.setTitle("MainScreen");

        if(chosenAcc == "ordinary")
        {
            String temp = Database.getOrdinaryAccountNumber(user.username);
            AccNumber.setText(temp);
            AccType.setText("Wybrane konto: ordinary");
        }
        else if(chosenAcc == "saving")
        {
            AccNumber.setText(Database.getSavingsAccountNumber( user.username));
            AccType.setText("Wybrane konto: saving");
        }
        /*
            list1.setListData(options);
            list1.getSelectionModel().addListSelectionListener(e ->
            {
                int index = list1.getSelectedIndex();
                if(index!=-1) {
                    if (options[index] == "one") {

                        JFrame okienko = new JFrame();
                        okienko.setVisible(true);

                    }else if(options[index] == "two")
                    {

                    }
                }
            });
        */
//Incountry Transfer, Foreign Transfer, Own Transfer, Standing Order Transfer, BLIK Phone Transfer
        incountryButton.addActionListener(e->
        {
            frame.dispose();
            try {
                new TransferFactory(AccountChoosed.ORDINARYACCOUNT,user,new MainFrame()).getTransfer(TransferFactory.TransferType.KRAJOWY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FontFormatException fontFormatException) {
                fontFormatException.printStackTrace();
            }
        });
        foreignTransferButton.addActionListener(e->
        {
            frame.dispose();
            try {
                new TransferFactory(AccountChoosed.ORDINARYACCOUNT,user,new MainFrame()).getTransfer(TransferFactory.TransferType.ZAGRANICZNY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FontFormatException fontFormatException) {
                fontFormatException.printStackTrace();
            }
        });
        ownTransferButton.addActionListener(e->
        {
            frame.dispose();
            try {
                new TransferFactory(AccountChoosed.ORDINARYACCOUNT,user,new MainFrame()).getTransfer(TransferFactory.TransferType.WLASNY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FontFormatException fontFormatException) {
                fontFormatException.printStackTrace();
            }
        });
        standingOrderTransferButton.addActionListener(e->{
            frame.dispose();
            try {
                new TransferFactory(AccountChoosed.ORDINARYACCOUNT,user,new MainFrame()).getTransfer(TransferFactory.TransferType.ZLECENIESTALE);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FontFormatException fontFormatException) {
                fontFormatException.printStackTrace();
            }
        });
        BLIKTransferButton.addActionListener(e->{
            frame.dispose();
            try {
                new TransferFactory(AccountChoosed.ORDINARYACCOUNT,user,new MainFrame()).getTransfer(TransferFactory.TransferType.TELEFONBLIK);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (FontFormatException fontFormatException) {
                fontFormatException.printStackTrace();
            }
        });
        ordinaryButton.addActionListener(e->{
            frame.dispose();
            try {
                new OrdinaryHistory(new MainFrame(),user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        savingsButton.addActionListener(e->{
            frame.dispose();
            try {
                new SavingsHistory(new MainFrame(),user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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



        //Auto close the window
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                counter = 0;
            }
        });
        new Thread() {
            public void run()
            {
                while(counter <= 120)
                {
                    if(frame.isActive())
                        timeCounter.setText("Time before log out: " + (120 - counter++));
                        else
                        counter = 0;

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {}
                }

                frame.dispose();
            }
        }.start();
        //Auto close the window

        frame.setContentPane(AuthPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        User test_user = new User();
        test_user.username="test_user";
        new MainScreen(test_user,null,null, "ordinary").CreateScreen();
    }


}

