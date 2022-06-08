package src.Credits;

import src.Database;
import src.Screen;
import src.User;

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
                if(1==0)//jezeli uzitkownik nie ma kredytu
                {
                        if(yesCheckBox.isSelected())
                        {
                            jpane.setMessage("Now you must pay us some money, hehe");

                            //dodac do bazy danych kwote kredytu, date i na jaki czas
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
        Balance.setText(String.valueOf(Database.getSavingsAccountBalance(user.username)));
        //MyCreditAmount.setText();
        //MyPayedCredit.setText();
        //MyDebt.setText();
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
    public double checkDebt(User user)
    {
        double creditAmount = 0;
        double currentCreditPayment = 0;
        double yearsAll = 0;
        int currentYear = 0;
        double needToPay = 0;
        double percent = 0.05;
        for (int i = 0; i < currentYear; i++)
        {
            needToPay=creditAmount/yearsAll + (creditAmount - i*creditAmount/yearsAll)*percent;
        }

            return (needToPay - currentCreditPayment);


    }

    public static void main(String[] args) throws IOException {
        User test_user = new User();
        test_user.username="test_user";
        new Credit(test_user,null,null).CreateScreen();
    }

}
