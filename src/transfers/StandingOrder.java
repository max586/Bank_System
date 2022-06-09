package src.transfers;

import src.Database;
import src.mainFrame.MainFrame;
import src.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class StandingOrder extends StandardTransfer implements Transfer {
    private AccountChoosed accountchoosedUser;
    public StandingOrder(AccountChoosed accountChoosed1,User user1,MainFrame mainFrame) throws IOException, FontFormatException {
        super(accountChoosed1,user1,mainFrame);
        accountchoosedUser = accountChoosed1;
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
                if(!Database.verifyOrdinaryAccountNumber(countryISO+accountNumberTxt.getText())){
                    if(!Database.verifySavingsAccountNumber(countryISO+accountNumberTxt.getText())){
                        validation.add(false);
                        accountNumberWarning.setText("Podany numer konta nie istnieje");
                        accountNumberWarning.setVisible(true);
                    }
                    else{
                        validation.add(true);
                        receiver.savings_account_number = countryISO+accountNumberTxt.getText();
                        accountChoosedReceiver = AccountChoosed.SAVINGSACCOUNT;
                        accountNumberWarning.setVisible(false);
                    }
                }
                else{
                    validation.add(true);
                    receiver.ordinary_account_number = countryISO+accountNumberTxt.getText();
                    accountChoosedReceiver = AccountChoosed.ORDINARYACCOUNT;
                    accountNumberWarning.setVisible(false);
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
                    receiver.firstName = receiverName1Txt.getText();
                    if(receiverNameCombo.getSelectedItem() == "Osoba") receiver.lastName = receiverName2Txt.getText();
                    else receiver.lastName = "";
                    if(isAddress) {
                        receiver.city = townNameTxt.getText();
                        receiver.post_code = postcode1Txt.getText() + "-" + postcode2Txt.getText();
                        receiver.street = streetNameTxt.getText();
                        if (streetNumber2Txt.getText().length() > 0)
                            receiver.street_nr = streetNumber1Txt.getText() + "/" + streetNumber2Txt.getText();
                        else receiver.street_nr = streetNumber1Txt.getText();
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
                        StandingOrderNextStep nextStep = new StandingOrderNextStep(accountchoosedUser,user,accountChoosedReceiver, receiver, transferData, frame, transferPanel1);
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