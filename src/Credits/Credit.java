package src.Credits;
import java.util.Date;
import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;
import java.lang.Object;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import java.sql.Statement;

public class Credit extends Screen
{
    public JPanel CreditPanel;
    public JLabel CreditLogo;
    public JLabel AccNum;
    public JTextField Amount;
    public JTextField Years;
    public JCheckBox yesCheckBox;
    public JLabel Balance;
    public JButton takeNewCreditButton;
    public JLabel MyCreditAmount;
    public JLabel MyPayedCredit;
    public JLabel MyDebt;
    public JButton payDebtButton;
    public JButton prevButton;
    public JOptionPane jpane;
    public JDialog jdialog;


    public Credit(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        jpane = new JOptionPane();
        jdialog=jpane.createDialog(CreditPanel,"");

        takeNewCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!Database.hasCredit(user.username))//jezeli uzitkownik nie ma kredytu
                {
                        if(yesCheckBox.isSelected())
                        {
                            if(!DataValidation.isNumber(Amount.getText()))
                            {
                                Amount.setText("Error");
                            }
                            if(!DataValidation.isNumber(Years.getText()) )
                            {

                                    Years.setText("Error");
                            }
                            if(DataValidation.isNumber(Years.getText()) )
                            {
                                Years.setText(Integer.toString((int)Float.parseFloat(Years.getText())));
                            }

                                if(DataValidation.isNumber(Amount.getText()) && DataValidation.isNumber(Years.getText()) &&  (int)Float.parseFloat(Years.getText())>0)
                                {
                                    jpane.setMessage("Now you must pay us some money, hehe");
                                    Date Today = new Date();
                                    //Years.setText(Integer.toString((int)Float.parseFloat(Years.getText())));
                                    String mounth = "";
                                    if (Today.getMonth()<10)
                                    {
                                        mounth = "0"+String.valueOf(Today.getMonth());
                                    }
                                    else
                                    {
                                        mounth = String.valueOf(Today.getMonth());
                                    }
                                    Database.addCredit(user.username, Float.parseFloat(Amount.getText()), 0, Today.getYear()+1900+"-"+mounth+"-"+Today.getDate() ,(int)Float.parseFloat(Years.getText()));
                                    String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
                                    MyCreditAmount.setText(CreditInfo[0]);
                                    MyPayedCredit.setText(CreditInfo[1]);
                                    MyDebt.setText(String.valueOf(checkDebt()));
                                }else
                                {
                                    jpane.setMessage("You are allowed to type only numbers nad year must be > 0");
                                }

                        }else
                        {
                            jpane.setMessage("you must accept the terms to take the credit ");
                        }

                }else
                {
                    jpane.setMessage("You already have a credit");
                }
                jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                jdialog.setTitle("Info");
                jdialog.setSize(550,170);
                jdialog.setVisible(true);
            }
        });
        payDebtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Database.hasCredit(user.username))
                {
                    String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
                    float UserBalance = Database.getOrdinaryAccountBalance(user.username);
                    float CreditDebt = checkDebt();
                    float CreditPayed = Float.parseFloat(CreditInfo[1]);

                    if (UserBalance - CreditDebt >= 0)
                    {
                        Database.setOrdinaryAccountBalance(user.username, UserBalance - CreditDebt);
                        Database.setCreditAmountPayed(user.username, CreditPayed + CreditDebt);
                        CreditInfo = Database.getCredit(user.username);
                        Balance.setText(String.valueOf(Database.getOrdinaryAccountBalance(user.username)));
                        Amount.setText("");
                        Years.setText("");
                        MyCreditAmount.setText(CreditInfo[0]);
                        MyPayedCredit.setText(CreditInfo[1]);
                        MyDebt.setText(String.valueOf(checkDebt()));

                        //Date StartDate = new Date(2000-1900,4,8);//ile Lat juz się ma kredyt
                       Date StartDate = new Date(Integer.parseInt(CreditInfo[2].substring(0, 3))-1900 , Integer.parseInt(CreditInfo[2].substring(5, 6)), Integer.parseInt(CreditInfo[2].substring(8, 9)));
                       System.out.println(StartDate);
                        jpane.setMessage("You payed your debt");
                        Date Today = new Date();
                        Date DiffOfYears = new Date(Today.getTime() - StartDate.getTime());
                        int currentYear = DiffOfYears.getYear() - 70;

                        if (currentYear >= Integer.parseInt(CreditInfo[3]) && checkDebt() == 0) ;
                        {
                            Database.deleteCredit(user.username);
                            MyCreditAmount.setText("0");
                            MyPayedCredit.setText("0");
                            MyDebt.setText("0");
                        }
                    }else
                    {
                        jpane.setMessage("You don't have enough money");
                    }
                }else
                {
                    jpane.setMessage("You don't have credit");
                }
                jpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                jdialog.setTitle("Info");
                jdialog.setSize(450,170);
                jdialog.setVisible(true);
            }
        });
    }
    public void CreateScreen() {

        frame = new JFrame();

        frame.setTitle("Credit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(CreditPanel);

        AccNum.setText(Database.getOrdinaryAccountNumber(user.username));
        Balance.setText(String.valueOf(Database.getOrdinaryAccountBalance(user.username)));

        if(Database.hasCredit(user.username))
        {
            String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
            MyCreditAmount.setText(CreditInfo[0]);
        MyPayedCredit.setText(CreditInfo[1]);
        MyDebt.setText(String.valueOf(checkDebt()));
        }
        else
        {
            MyCreditAmount.setText("0");
            MyPayedCredit.setText("0");
            MyDebt.setText("0");
        }

        prevButton.addActionListener(e->
        {
            frame.dispose();
            if(prev_screen!=null){
                prev_screen.frame.setVisible(true);
            }

        });
        frame.setSize(1080, 720);
        frame.setVisible(true);
    }
    public float checkDebt()
    {
        String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
        float creditAmount = Float.parseFloat(CreditInfo[0]);
        float currentCreditPayment = Float.parseFloat(CreditInfo[1]);
        float yearsAll = Float.parseFloat(CreditInfo[3]);
        Date StartDate = new Date(Integer.parseInt(CreditInfo[2].substring(0,4))-1900,Integer.parseInt(CreditInfo[2].substring(5,7)),Integer.parseInt(CreditInfo[2].substring(8,10)));
        //Date StartDate = new Date(2000-1900,4,8);//ile Lat juz się ma kredyt
        Date Today = new Date();
        Date DiffOfYears = new Date(Today.getTime() - StartDate.getTime());
        int currentYear = DiffOfYears.getYear()-70;

        float needToPay = 0;
        float percent = (float)0.05;
        for (int i = 0; i < currentYear && i < yearsAll; i++)
        {
            needToPay+=creditAmount/yearsAll + (creditAmount - i*creditAmount/yearsAll)*percent;
        }
            return (needToPay - currentCreditPayment);


    }

    public static void main(String[] args) throws IOException {
        User test_user = new User();
        test_user.username="test_user";
        new Credit(test_user,null,null).CreateScreen();
    }

}
