package src.transfers;

import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.Map;

public class TransferNextStep {
    public JLabel senderName;
    public JLabel senderSurname;
    public JLabel receiverName;
    public JLabel receiverSurname;
    public JLabel senderAccountNumber;
    public JLabel receiverAccountNumber;
    public JLabel transferTitle;
    public JLabel transferAmount;
    public JLabel transferPayment;
    public JLabel transferType;
    public JButton cancelButton;
    public JButton nextButton;
    public Map<String,String> transferData;
    public JPanel transferNextStepPanel;
    public JLabel currencyLabel;
    public JLabel transferPanelTitleLabel;
    public JLabel equalsLabel;
    public JLabel polishCurrencyAmountLabel;
    public JLabel polishCurrencyLabel;
    public JLabel transferPaymentCurrencyLabel;
    public JLabel feeLabel;
    public JLabel dateFrom;
    public JLabel dateToLabel;
    public JLabel dateTo;
    public JPanel timerPanel;
    public JLabel timeLabel;
    public String transferPanelTitle;
    public JPanel cancelPanel;
    public MainFrame frame;
    public User user;
    public User receiver;
    public AccountChoosed accountChoosedUser;
    public AccountChoosed accountChoosedReceiver;
    public String userAccountNumber;
    public String receiverAccountNr;
    public double userAccountBalance;
    public TransferNextStep(AccountChoosed accountChoosed1, User user1, AccountChoosed accountChoosed2, User receiver1, Map<String,String> transferData1,MainFrame mainFrame, JPanel transferPanel){
        accountChoosedUser = accountChoosed1;
        accountChoosedReceiver = accountChoosed2;
        user = user1;
        receiver = receiver1;
        if(accountChoosedUser==AccountChoosed.ORDINARYACCOUNT) {
            userAccountNumber = user.ordinary_account_number;
            userAccountBalance = Math.round(user.ordinary_account_balance*100.0)/100.0;
        }else{
            userAccountNumber = user.savings_account_number;
            userAccountBalance = Math.round(user.savings_account_balance*100.0)/100.0;
        }
        if(accountChoosedReceiver==AccountChoosed.ORDINARYACCOUNT) {
            receiverAccountNr = receiver.ordinary_account_number;
        }else{
            receiverAccountNr = receiver.savings_account_number;
        }
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,mainFrame);
        transferNextStepPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        cancelPanel = transferPanel;
        transferData = transferData1;
        setLabels();
        setNextButton(nextButton);
        setCancelButton(cancelButton);
    }

    void setLabels(){
        dateToLabel.setVisible(false);
        dateFrom.setVisible(false);
        dateTo.setVisible(false);
        equalsLabel.setVisible(false);
        polishCurrencyAmountLabel.setVisible(false);
        polishCurrencyLabel.setVisible(false);
        if(transferData.get("typ").equals("BLIK Phone Transfer")){
            transferPanelTitle = transferData.get("typ");
            transferPanelTitleLabel.setText(transferPanelTitle);
        }
        else {
            String[] arr = transferData.get("typ").split("\\s+");
            transferPanelTitle = arr[0] + " " + arr[1];
            transferPanelTitleLabel.setText(transferPanelTitle);
            if (transferPanelTitle.equals("Foreign Transfer")) {
                equalsLabel.setVisible(true);
                if (arr[2].equals("natychmiastowy"))
                    polishCurrencyAmountLabel.setText(String.format("%.2f", Double.parseDouble(transferData.get("kwotaPLN")) - 5.00));
                else
                    polishCurrencyAmountLabel.setText(String.format("%.2f", Double.parseDouble(transferData.get("kwotaPLN"))));
                polishCurrencyAmountLabel.setVisible(true);
                polishCurrencyLabel.setVisible(true);
            }
        }
        if(transferPanelTitle.equals("Standing Order")){
            feeLabel.setText("Start date");
            dateToLabel.setText("End date");
            dateFrom.setText(transferData.get("startdata"));
            dateFrom.setVisible(true);
            if(transferData.containsKey("enddata")) {
                dateTo.setText(transferData.get("enddata"));
                dateToLabel.setVisible(true);
                dateTo.setVisible(true);
            }
            transferPayment.setVisible(false);
            transferPaymentCurrencyLabel.setVisible(false);
        }
        senderName.setText(user.firstName);
        senderSurname.setText(user.lastName);
        receiverName.setText(receiver.firstName);
        receiverSurname.setText(receiver.lastName);
        if(transferPanelTitle.equals("BLIK Phone Transfer")){
            senderAccountNumber.setText("Account nr.: "+userAccountNumber);
            receiverAccountNumber.setText("Phone nr.: "+receiver.phone_number);
        }
        else {
            senderAccountNumber.setText(userAccountNumber);
            receiverAccountNumber.setText(receiverAccountNr);
        }
        transferTitle.setText(transferData.get("tytul"));
        transferAmount.setText(transferData.get("kwota"));
        transferPayment.setText(transferData.get("oplata"));
        transferType.setText(transferData.get("typ"));
        currencyLabel.setText(transferData.get("waluta"));
    }
    void setNextButton(JButton nextButton){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferConfirm pO = new TransferConfirm(accountChoosedUser, user, accountChoosedReceiver, receiver, transferData, frame, transferNextStepPanel);
                frame.getjFrame().setContentPane(pO.getTransferConfirmPanel());
                frame.getjFrame().setVisible(true);
            }
        });
    }

    void setCancelButton(JButton cancelButton){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(cancelPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }
    JPanel getTransferNextStepPanel(){
        return transferNextStepPanel;
    }
}