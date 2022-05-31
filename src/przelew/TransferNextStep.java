package przelew;

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
    private JPanel cancelPanel;
    private MainFrame frame;

    public TransferNextStep(MainFrame mainFrame, JPanel transferPanel, Map<String,String> senderData1, Map<String,String> receiverData1, Map<String,String> receiverData, double senderAmount){
        frame = mainFrame;
        cancelPanel = transferPanel;
        senderData = senderData1;
        this.receiverData = receiverData1;
        transferData = receiverData;
        setLabels();
        setNextButton(nextButton);
        setCancelButton(cancelButton);
    }

    void setLabels(){
        senderName.setText(senderData.get("nazwa odbiorcy"));
        senderSurname.setText(senderData.get("nazwa odbiorcy cd"));
        receiverName.setText(receiverData.get("nazwa odbiorcy"));
        receiverSurname.setText(receiverData.get("nazwa odbiorcy cd"));
        senderAccountNumber.setText(senderData.get("nr konta"));
        receiverAccountNumber.setText(receiverData.get("nr konta"));
        transferTitle.setText(transferData.get("tytul"));
        transferAmount.setText(transferData.get("kwota"));
        transferPayment.setText(transferData.get("oplata"));
        transferType.setText(transferData.get("typ"));
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
