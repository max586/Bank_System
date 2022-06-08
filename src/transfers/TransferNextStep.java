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
    public Map<String,String> senderData;
    public Map<String,String> receiverData;
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
    public TransferNextStep(User user1, MainFrame mainFrame, JPanel transferPanel, Map<String,String> senderData1, Map<String,String> receiverData1, Map<String,String> transferData1){
        user = user1;
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,mainFrame);
        transferNextStepPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        cancelPanel = transferPanel;
        senderData = senderData1;
        this.receiverData = receiverData1;
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
        if(transferData.get("typ").equals("Przelew BLIK na telefon")){
            transferPanelTitle = transferData.get("typ");
            transferPanelTitleLabel.setText(transferPanelTitle);
        }
        else {
            String[] arr = transferData.get("typ").split("\\s+");
            transferPanelTitle = arr[0] + " " + arr[1];
            transferPanelTitleLabel.setText(transferPanelTitle);
            if (transferPanelTitle.equals("Przelew zagraniczny")) {
                equalsLabel.setVisible(true);
                if (arr[2].equals("natychmiastowy"))
                    polishCurrencyAmountLabel.setText(String.format("%.2f", Double.parseDouble(transferData.get("kwotaPLN")) - 5.00));
                else
                    polishCurrencyAmountLabel.setText(String.format("%.2f", Double.parseDouble(transferData.get("kwotaPLN"))));
                polishCurrencyAmountLabel.setVisible(true);
                polishCurrencyLabel.setVisible(true);
            }
        }
        if(transferPanelTitle.equals("Zlecenie stałe")){
            feeLabel.setText("Obowiązuje od");
            dateToLabel.setText("Obowiązuje do");
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
        senderName.setText(senderData.get("nazwa odbiorcy"));
        senderSurname.setText(senderData.get("nazwa odbiorcy cd"));
        receiverName.setText(receiverData.get("nazwa odbiorcy"));
        receiverSurname.setText(receiverData.get("nazwa odbiorcy cd"));
        if(transferPanelTitle.equals("Przelew BLIK na telefon")){
            senderAccountNumber.setText("Nr. konta: "+senderData.get("nr konta"));
            receiverAccountNumber.setText("Nr. telefonu: "+receiverData.get("nr telefonu"));
        }
        else {
            senderAccountNumber.setText(senderData.get("nr konta"));
            receiverAccountNumber.setText(receiverData.get("nr konta"));
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
                TransferConfirm pO = new TransferConfirm(user,frame, transferNextStepPanel,senderData,receiverData,transferData);
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
