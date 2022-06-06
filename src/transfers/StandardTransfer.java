package transfers;

import mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StandardTransfer implements Transfer{
    protected JTextField accountNumberTxt;
    protected JTextField transferAmount1Txt;
    protected JComboBox receiverNameCombo;
    protected JTextField receiverName1Txt;
    protected JTextField receiverName2Txt;
    protected JTextField townNameTxt;
    protected JTextField streetNameTxt;
    protected JTextField postcode1Txt;
    protected JTextField postcode2Txt;
    protected JTextField streetNumber1Txt;
    protected JTextField streetNumber2Txt;
    protected JPanel transferPanel1;
    protected JLabel receiverName1Label;
    protected JLabel receiverName2Label;
    protected JTextField transferAmount2Txt;
    protected JTextArea transferTitleTextArea;
    protected JRadioButton expressTransferRadioButton;
    protected JButton nextButton;
    protected JButton cancelButton;
    protected JLabel accountNumberWarning;
    protected JLabel availableFundsLabel;
    protected JLabel transferAmountWarning;
    protected JLabel expressTransferWarning;
    protected JLabel postcodeWarning;
    protected JLabel transferTitleWarning;
    protected JLabel receiverName1Warning;
    protected JLabel receiverName2Warning;
    protected JLabel townNameWarning;
    protected JLabel streetNameWarning;
    protected JLabel streetNumberWarning;
    protected JTextField countryNameTxt;
    protected JLabel countryNameLabel;
    protected JLabel countryNameWarning;
    protected JRadioButton receiverAddressRadioButton;
    protected JLabel townNameLabel;
    protected JLabel streetNameLabel;
    protected JLabel postcodeLabel;
    protected JLabel streetNumberLabel;
    protected JLabel postcodeDashLabel;
    protected JLabel streetNumberSlashLabel;
    protected JLabel panelTitleLabel;
    protected JLabel accountNumberCountryLabel;
    protected JLabel transferAmountCurrencyLabel;
    protected JComboBox currencyComboBox;
    protected JLabel accountNumberLabel;
    protected JLabel currencyInfoLabel;
    protected JComboBox accountNumberCountryComboBox;
    protected JLabel[] warnings = {accountNumberWarning, transferAmountWarning, expressTransferWarning, postcodeWarning, transferTitleWarning, receiverName1Warning, receiverName2Warning, townNameWarning, streetNameWarning, streetNumberWarning};
    protected boolean isCompany;
    protected boolean isPerson;
    protected boolean isAccountNumberValid;
    protected boolean isAmountValid;
    protected boolean isPostCodeValid;
    protected boolean isPayment;
    protected KeyAdapter numbersOnly;
    protected double transferAmount1 = 0.0;
    protected double transferAmount2 = 0.0;
    protected double finalTransferAmount = 0.0;
    protected Map<String,String> receiverData;
    protected Map<String,String> transferData;
    protected boolean buttonValid;
    protected Vector<Boolean> validation;
    protected boolean isAddress;
    protected Map<String,String> senderData;
    protected double senderAmount;
    protected String countryISO;
    protected boolean isCountry;
    protected MainFrame frame;
    public StandardTransfer(){}
    public StandardTransfer(MainFrame mainFrame, Map<String, String> senderData1) throws IOException, FontFormatException {
        isCountry = false;
        countryISO = "PL ";
        frame = mainFrame;
        senderData = senderData1;
        senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
        receiverData = new HashMap<>();
        transferData = new HashMap<>();
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        setLabels();
        setCurrency();
        setReceiverNameCombo();
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
        setPostcodeTxt(postcode1Txt);
        setPostcodeTxt(postcode2Txt);
        setAccountNumberTxt(accountNumberTxt);
        setExpressTransferRadioButton(expressTransferRadioButton);
        setReceiverAddressRadioButton(receiverAddressRadioButton);
        setNextButton(nextButton);
        transferPanel1.revalidate();
        frame.getjFrame().revalidate();
        frame.getjFrame().setContentPane(transferPanel1);
        frame.getjFrame().setVisible(true);
    }

    void setCurrency(){
        transferData.put("waluta","PLN");
    }
    void setLabels(){
        currencyInfoLabel.setVisible(false);
        accountNumberCountryComboBox.setVisible(false);
        currencyComboBox.setVisible(false);
        countryNameLabel.setVisible(false);
        countryNameTxt.setVisible(false);
        countryNameWarning.setVisible(false);
        townNameTxt.setVisible(false);
        townNameLabel.setVisible(false);
        postcode1Txt.setVisible(false);
        postcode2Txt.setVisible(false);
        postcodeLabel.setVisible(false);
        streetNameLabel.setVisible(false);
        streetNameTxt.setVisible(false);
        streetNumberLabel.setVisible(false);
        streetNumber1Txt.setVisible(false);
        streetNumber2Txt.setVisible(false);
        postcodeDashLabel.setVisible(false);
        streetNumberSlashLabel.setVisible(false);
        for(JLabel w: warnings) w.setVisible(false);
        receiverName1Label.setVisible(false);
        receiverName2Label.setVisible(false);
        receiverName1Txt.setVisible(false);
        receiverName2Txt.setVisible(false);
        availableFundsLabel.setText(String.valueOf(senderAmount));
    }

    void setReceiverNameCombo(){
        receiverNameCombo.addActionListener(event -> {
            JComboBox c = (JComboBox) event.getSource();
            receiverName1Warning.setVisible(false);
            receiverName2Warning.setVisible(false);
            if (c.getSelectedItem() == "Firma") {
                isCompany = true;
                if (isPerson) {
                    receiverName2Label.setVisible(false);
                    receiverName2Txt.setVisible(false);
                }
                receiverName1Label.setText("Nazwa firmy");
                receiverName1Label.setVisible(true);
                receiverName1Txt.setVisible(true);
                receiverName2Warning.setVisible(false);
            } else if (c.getSelectedItem() == "Osoba") {
                isPerson = true;
                if (isCompany) {
                    receiverName1Label.setVisible(false);
                    receiverName1Txt.setVisible(false);
                }
                receiverName1Label.setText("Imię");
                receiverName1Label.setVisible(true);
                receiverName1Txt.setVisible(true);
                receiverName2Label.setVisible(true);
                receiverName2Txt.setVisible(true);
            } else {
                receiverName1Label.setVisible(false);
                receiverName1Txt.setVisible(false);
                receiverName2Label.setVisible(false);
                receiverName2Txt.setVisible(false);
            }
        });
    }

    void setTransferAmountTxt(JTextField transferAmountTxt){
        if(transferAmountTxt==transferAmount2Txt){
            transferAmountTxt.setDocument(new LimitJTextField(2));
            transferAmountTxt.setText("00");
        }
        transferAmountTxt.addKeyListener(numbersOnly);
        transferAmountTxt.addKeyListener(new KeyAdapter() {
            String s1 = "";
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()>='0'&& e.getKeyChar()<='9'){
                    s1+=e.getKeyChar();
                }
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&s1.length()>0){
                    s1 = s1.substring(0,s1.length()-1);
                }
                if(s1.length()>0) {
                    double k1;
                    if(transferAmountTxt==transferAmount1Txt){
                        k1 = Double.parseDouble(s1);
                        transferAmount1 =k1;
                        finalTransferAmount = transferAmount1+transferAmount2;
                        isAmountValid = !(finalTransferAmount > senderAmount);
                    }
                    else{
                        k1 = Double.parseDouble("0." + s1);
                        transferAmount2=k1;
                        finalTransferAmount = transferAmount1+transferAmount2;
                        isAmountValid = !(finalTransferAmount>senderAmount);
                    }
                    transferAmountWarning.setText("Nie masz wystarczających środków");
                    transferAmountWarning.setVisible(!isAmountValid);
                }
            }
        });
    }

    void setExpressTransferRadioButton(JRadioButton expressTransferRadioButton) {
        expressTransferRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                System.out.println(finalTransferAmount);
                if (state == ItemEvent.SELECTED) {
                    finalTransferAmount += 5.00;
                    if (finalTransferAmount > senderAmount) {
                        expressTransferWarning.setVisible(true);
                        isAmountValid = false;
                    }
                    isPayment = true;
                } else if (state == ItemEvent.DESELECTED) {
                    finalTransferAmount -= 5.00;
                    expressTransferWarning.setVisible(false);
                    isAmountValid = true;
                    isPayment = false;
                }
            }
        });
    }

    void setPostcodeTxt(JTextField postcodeTxt){
        if(postcodeTxt==postcode1Txt) postcodeTxt.setDocument(new LimitJTextField(2));
        else postcodeTxt.setDocument(new LimitJTextField(3));
        postcodeTxt.addKeyListener(numbersOnly);
        postcodeTxt.addKeyListener(new KeyAdapter() {
            String s1 = "";
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()>='0'&& e.getKeyChar()<='9'){
                    s1+=e.getKeyChar();
                }
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&s1.length()>0){
                    s1 = s1.substring(0,s1.length()-1);
                }
                if(postcodeTxt==postcode1Txt) isPostCodeValid = (s1.length() == 2);
                else isPostCodeValid = (s1.length()==3);
                postcodeWarning.setText("Niepoprawny kod pocztowy");
                postcodeWarning.setVisible(!isPostCodeValid);
            }
        });
    }

    void setAccountNumberTxt(JTextField accountNumberTxt){
        accountNumberTxt.setDocument(new LimitJTextField(26));
        accountNumberTxt.addKeyListener(numbersOnly);
        accountNumberTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(accountNumberTxt.getText().length()<25){
                    isAccountNumberValid = false;
                }
                else {
                    isAccountNumberValid = e.getKeyChar() != KeyEvent.VK_BACK_SPACE;
                }
                accountNumberWarning.setText("Niepoprawny numer rachunku");
                accountNumberWarning.setVisible(!isAccountNumberValid);
            }
        });
    }

    void setReceiverAddressRadioButton(JRadioButton receiverAddressRadioButton){
        receiverAddressRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    if(isCountry){
                        countryNameLabel.setVisible(true);
                        countryNameTxt.setVisible(true);
                    }
                    townNameTxt.setVisible(true);
                    townNameLabel.setVisible(true);
                    postcode1Txt.setVisible(true);
                    postcode2Txt.setVisible(true);
                    postcodeLabel.setVisible(true);
                    streetNameLabel.setVisible(true);
                    streetNameTxt.setVisible(true);
                    streetNumberLabel.setVisible(true);
                    streetNumber1Txt.setVisible(true);
                    streetNumber2Txt.setVisible(true);
                    postcodeDashLabel.setVisible(true);
                    streetNumberSlashLabel.setVisible(true);
                    isAddress = true;
                } else if (state == ItemEvent.DESELECTED) {

                    townNameTxt.setVisible(false);
                    townNameLabel.setVisible(false);
                    postcode1Txt.setVisible(false);
                    postcode2Txt.setVisible(false);
                    postcodeLabel.setVisible(false);
                    streetNameLabel.setVisible(false);
                    streetNameTxt.setVisible(false);
                    streetNumberLabel.setVisible(false);
                    streetNumber1Txt.setVisible(false);
                    streetNumber2Txt.setVisible(false);
                    postcodeDashLabel.setVisible(false);
                    streetNumberSlashLabel.setVisible(false);
                    postcodeWarning.setVisible(false);
                    streetNumberWarning.setVisible(false);
                    streetNameWarning.setVisible(false);
                    townNameWarning.setVisible(false);
                    isAddress = false;
                }
            }
        });
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
                    TransferNextStep pCd = new TransferNextStep(frame, transferPanel1,senderData,receiverData, transferData);
                    frame.getjFrame().setContentPane(pCd.getTransferNextStepPanel());
                    frame.getjFrame().setVisible(true);
                }
            }
        });
    }
}



class OnlyNumbers{
    KeyAdapter keyAdapter;
    public OnlyNumbers(){
        keyAdapter = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        };
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}

class LimitJTextField extends PlainDocument {
    private final int max;
    LimitJTextField(int max) {
        super();
        this.max = max;
    }
    public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text == null)
            return;
        if ((getLength() + text.length()) <= max) {
            super.insertString(offset, text, attr);
        }
    }
}
