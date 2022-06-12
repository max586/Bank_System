package src.Credits;
import java.util.Date;
import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

import java.lang.Object;
import java.util.Date;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import java.sql.Statement;
import java.text.DecimalFormat;


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
    private JLabel timeLabel;
    public JOptionPane jpane;
    public JDialog jdialog;
    private static DecimalFormat df = new DecimalFormat("#.##");

    public Credit(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        CreditPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

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
                        if(isProvidedDataIsValid())
                        {
                            jpane.setMessage("Now you have the credit");
                            Date Today = new Date();
                            user.ordinary_account_balance+=Float.parseFloat(Amount.getText());
                            Database.setOrdinaryAccountBalance(user.username,Database.getOrdinaryAccountBalance(user.username) + Float.parseFloat(Amount.getText()));
                            //Years.setText(Integer.toString((int)Float.parseFloat(Years.getText())));
                            Database.addCredit(user.username, Float.parseFloat(Amount.getText()), 0, convertDateToString(Today),(int)Float.parseFloat(Years.getText()));
                            String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
                            Balance.setText(String.valueOf(Database.getOrdinaryAccountBalance(user.username)));
                            MyCreditAmount.setText(CreditInfo[0]);
                            MyPayedCredit.setText(CreditInfo[1]);
                            MyDebt.setText(String.valueOf(checkDebt()));
                        }else
                        {
                            jpane.setMessage("You are allowed to type only numbers nad year must be from 1 to 50");
                        }

                    }else
                    {
                        jpane.setMessage("you must accept the terms to take the credit");
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
        payDebtButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Database.hasCredit(user.username))
                {
                    String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
                    float UserBalance = Database.getOrdinaryAccountBalance(user.username);
                    float CreditDebt = checkDebt();
                    float CreditPayed = Float.parseFloat(CreditInfo[1]);

                    if (hasEnoughtMoney(UserBalance, CreditDebt))
                    {
                        user.ordinary_account_balance=UserBalance-CreditDebt;
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
                        Date Today = new Date();
                        Date StartDate = new Date(Integer.parseInt(CreditInfo[2].substring(0,4))-1900,Integer.parseInt(CreditInfo[2].substring(5,7)),Integer.parseInt(CreditInfo[2].substring(8,10)));
                        jpane.setMessage("You payed your debt");
                        // Date DiffOfYears = new Date(Today.getTime() - StartDate.getTime());
                        int currentYear = diffOfYear(Today,StartDate);//DiffOfYears.getYear() - 70;
                        //System.out.println(currentYear+" "+Integer.parseInt(CreditInfo[3]));
                        if (currentYear >= Integer.parseInt(CreditInfo[3]) && checkDebt() == 0)
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
    public boolean hasEnoughtMoney(float UserBalance,float creditDebt ){
        return (UserBalance - creditDebt >= 0);
    }
    public void CreateScreen() {

        //frame = new JFrame();

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
    public boolean isProvidedDataIsValid()
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
        return (DataValidation.isNumber(Amount.getText()) && DataValidation.isNumber(Years.getText()) &&  (int)Float.parseFloat(Years.getText())>0 && (int)Float.parseFloat(Years.getText())<51);
    }
    public Date convertStringToDate(String dateInString)
    {
        return  new Date(Integer.parseInt(dateInString.substring(0,4))-1900,Integer.parseInt(dateInString.substring(5,7)),Integer.parseInt(dateInString.substring(8,10)));

    }

    public String convertDateToString(Date today)
    {
        String year = String.valueOf(today.getYear()+1900);

        String mounth = "";
        if (today.getMonth()+1<10)
        {
            mounth = "0"+String.valueOf(today.getMonth()+1);
        }
        else
        {
            mounth = String.valueOf(today.getMonth()+1);
        }
        String day = "";
        if (today.getDate()<10)
        {
            day = "0"+String.valueOf(today.getDate());
        }
        else
        {
            day = String.valueOf(today.getDate());
        }
        return (year+"-"+mounth+"-"+day);
    }
    public int  diffOfYear(Date today, Date startDate)
    {
        Date DiffOfYears = new Date(today.getTime() - startDate.getTime());
        return DiffOfYears.getYear()-70;
    }
    public float checkDebt()
    {
        String[] CreditInfo = Database.getCredit(user.username);//Amount , AmountPayed , StartDate , Duration
        float creditAmount = Float.parseFloat(CreditInfo[0]);
        float currentCreditPayment = Float.parseFloat(CreditInfo[1]);
        float yearsAll = Float.parseFloat(CreditInfo[3]);
        Date StartDate = convertStringToDate(CreditInfo[2]);
        Date Today = new Date();
        //Date StartDate = new Date(Integer.parseInt(CreditInfo[2].substring(0,4))-1900,Integer.parseInt(CreditInfo[2].substring(5,7)),Integer.parseInt(CreditInfo[2].substring(8,10)));
        //Date StartDate = new Date(2000-1900,4,8);//ile Lat juz się ma kredyt

        // Date DiffOfYears = new Date(Today.getTime() - StartDate.getTime());
        int currentYear = diffOfYear(Today,StartDate);//DiffOfYears.getYear()-70;

        float needToPay = 0;
        float percent = (float)0.05;
        for (int i = 0; i < currentYear && i < yearsAll; i++)
        {
            needToPay+=creditAmount/yearsAll + (creditAmount - i*creditAmount/yearsAll)*percent;
        }
        return Float.parseFloat( df.format(needToPay - currentCreditPayment));
        //return (needToPay - currentCreditPayment);
    }

    public static void main(String[] args) throws IOException {
        User test_user = new User();
        test_user.username="test_user";
        new Credit(test_user,null,null).CreateScreen();
    }

}

