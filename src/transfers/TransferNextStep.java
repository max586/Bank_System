package transfers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


/*
data: nr. konra, imie, nazwisko, {adres: Miejscowosc, Kod pocztowy, ulica, Nr. domu/ mieszkania}
 */

/*
    danePrzelewu: tytuł, kwota, oplata, typ
 */

public class TransferNextStep {
    private JLabel senderName;
    private JLabel senderSurname;
    private JLabel receiverName;
    private JLabel receiverSurname;
    private JLabel senderAccountNumber;
    private JLabel receiverAccountNumber;
    private JLabel transferTitle;
    private JLabel transferAmount;
    private JLabel transferPayment;
    private JLabel transferType;
    private JButton cancelButton;
    private JButton nextButton;
    private Map<String,String> senderData;
    private Map<String,String> receiverData;
    private Map<String,String> transferData;
    private JPanel transferNextStepPanel;
    private JLabel currencyLabel;
    private JLabel transferPanelTitleLabel;
    private JLabel equalsLabel;
    private JLabel polishCurrencyAmountLabel;
    private JLabel polishCurrencyLabel;
    private JLabel transferPaymentCurrencyLabel;
    private JLabel feeLabel;
    private JLabel dateFrom;
    private JLabel dateToLabel;
    private JLabel dateTo;
    private String transferPanelTitle;
    private JPanel cancelPanel;
    private MainFrame frame;

    public TransferNextStep(MainFrame mainFrame, JPanel transferPanel, Map<String,String> senderData1, Map<String,String> receiverData1, Map<String,String> transferData1, double senderAmount){
        frame = mainFrame;
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
                TransferConfirm pO = new TransferConfirm(frame, transferNextStepPanel,senderData,receiverData,transferData);
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
