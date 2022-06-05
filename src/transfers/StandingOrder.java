package transfers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class StandingOrder extends StandardTransfer implements Transfer{
    public StandingOrder(MainFrame mainFrame, Map<String, String> senderData1, double senderAmount1) throws IOException, FontFormatException {
        super(mainFrame,senderData1,senderAmount1);
        panelTitleLabel.setText("Zlecenie stałe");
        expressTransferRadioButton.setVisible(false);
    }
    void setNextButton(JButton nextButton){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                if(accountNumberTxt.getText().length()==0){
                    accountNumberWarning.setText("To pole jest wymagane");
                    accountNumberWarning.setVisible(true);
                    validation.add(false);
                }
                else {
                    accountNumberWarning.setVisible(false);
                    validation.add(true);
                }
                if(receiverNameCombo.getSelectedItem() == "Wybierz"){
                    receiverName1Warning.setVisible(true);
                    validation.add(false);
                }
                else{
                    receiverName1Warning.setVisible(false);
                    validation.add(true);
                }
                if(receiverNameCombo.getSelectedItem() == "Osoba"){
                    if(receiverName1Txt.getText().length()==0){
                        receiverName1Warning.setVisible(true);
                        validation.add(false);
                    }
                    else {
                        receiverName1Warning.setVisible(false);
                        validation.add(true);
                    }
                    if(receiverName2Txt.getText().length()==0){
                        receiverName2Warning.setVisible(true);
                        validation.add(false);
                    }
                    else {
                        receiverName2Warning.setVisible(false);
                        validation.add(true);
                    }
                }
                if(receiverNameCombo.getSelectedItem() == "Firma"){
                    if(receiverName1Txt.getText().length()==0){
                        receiverName1Warning.setVisible(true);
                        validation.add(false);
                    }
                    else {
                        receiverName1Warning.setVisible(false);
                        validation.add(true);
                    }
                }
                if(isAddress) {
                    if(isCountry){
                        if(countryNameTxt.getText().length()==0){
                            countryNameWarning.setVisible(true);
                            validation.add(false);
                        }
                        else {
                            countryNameWarning.setVisible(false);
                            validation.add(true);
                        }
                    }
                    if (townNameTxt.getText().length() == 0) {
                        townNameWarning.setVisible(true);
                        validation.add(false);
                    } else {
                        townNameWarning.setVisible(false);
                        validation.add(true);
                    }
                    if (streetNameTxt.getText().length() == 0) {
                        streetNameWarning.setVisible(true);
                        validation.add(false);
                    } else {
                        streetNameWarning.setVisible(false);
                        validation.add(true);
                    }
                    if (postcode1Txt.getText().length() == 0 || postcode2Txt.getText().length() == 0) {
                        postcodeWarning.setText("To pole jest wymagane");
                        postcodeWarning.setVisible(true);
                        validation.add(false);
                    } else {
                        postcodeWarning.setVisible(false);
                        validation.add(true);
                    }
                    if (streetNumber1Txt.getText().length() == 0) {
                        streetNumberWarning.setVisible(true);
                        validation.add(false);
                    } else {
                        streetNumberWarning.setVisible(false);
                        validation.add(true);
                    }
                }
                if(transferAmount1Txt.getText().length()==0){
                    transferAmountWarning.setText("To pole jest wymagane");
                    transferAmountWarning.setVisible(true);
                    validation.add(false);
                }
                else{
                    transferAmountWarning.setVisible(false);
                    validation.add(true);
                }
                if(transferTitleTextArea.getText().length()==0){
                    transferTitleWarning.setVisible(true);
                    validation.add(false);
                }
                else {
                    transferTitleWarning.setVisible(false);
                    validation.add(true);
                }
                if(finalTransferAmount>senderAmount){
                    transferAmountWarning.setText("Nie masz wystarczających środków");
                    transferAmountWarning.setVisible(true);
                    validation.add(false);
                }
                if(!isAccountNumberValid) validation.add(false);
                buttonValid = !validation.contains(false);
                if(buttonValid){
                    String nrKontaOdbiorcy = accountNumberTxt.getText();
                    StringBuilder result = new StringBuilder();
                    result.append(nrKontaOdbiorcy.charAt(0));
                    result.append(nrKontaOdbiorcy.charAt(1));
                    result.append(" ");
                    int j = 0;
                    for(int i=2; i<nrKontaOdbiorcy.length();++i){
                        result.append(nrKontaOdbiorcy.charAt(i));
                        ++j;
                        if(j%4==0) result.append(" ");
                    }
                    receiverData.put("nr konta",countryISO+String.valueOf(result));
                    receiverData.put("nazwa odbiorcy", receiverName1Txt.getText());
                    if(receiverNameCombo.getSelectedItem() == "Osoba") receiverData.put("nazwa odbiorcy cd", receiverName2Txt.getText());
                    else receiverData.put("nazwa odbiorcy cd","");
                    if(isAddress) {
                        if(isCountry) receiverData.put("kraj",countryNameTxt.getText());
                        receiverData.put("miejscowosc", townNameTxt.getText());
                        receiverData.put("kod pocztowy", postcode1Txt.getText() + "-" + postcode2Txt.getText());
                        receiverData.put("ulica", streetNameTxt.getText());
                        if (streetNumber2Txt.getText().length() > 0)
                            receiverData.put("nr domu", streetNumber1Txt.getText() + "/" + streetNumber2Txt.getText());
                        else receiverData.put("nr domu", streetNumber1Txt.getText());
                    }
                    transferData.put("tytul", transferTitleTextArea.getText());
                    transferData.put("kwota", transferAmount1Txt.getText()+"."+ transferAmount2Txt.getText());
                    transferData.put("kwotaPLN", String.valueOf(finalTransferAmount));
                    if(isPayment){
                        transferData.put("oplata","5.00");
                        transferData.put("typ",panelTitleLabel.getText()+" natychmiastowy");
                    }
                    else{
                        transferData.put("oplata","0.00");
                        transferData.put("typ",panelTitleLabel.getText()+" zwykły");
                    }
                    try {
                        StandingOrderNextStep nextStep = new StandingOrderNextStep(frame, transferPanel1,senderData,receiverData, transferData,senderAmount);
                        frame.getjFrame().setContentPane(nextStep.getStandingOrderNextPanel());
                        frame.getjFrame().setVisible(true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (FontFormatException fontFormatException) {
                        fontFormatException.printStackTrace();
                    }
                }
            }
        });
    }
}