package przelew;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Transfer extends JFrame{
    private JTextField accountNumberTxt;
    private JTextField transferAmount1Txt;
    private JComboBox receiverNameCombo;
    private JTextField receiverName1Txt;
    private JTextField receiverName2Txt;
    private JTextField townNameTxt;
    private JTextField streetNameTxt;
    private JTextField postcode1Txt;
    private JTextField postcode2Txt;
    private JTextField streetNumber1Txt;
    private JTextField streetNumber2Txt;
    private JPanel transferPanel;
    private JLabel receiverName1Label;
    private JLabel receiverName2Label;
    private JTextField transferAmount2Txt;
    private JTextArea transferTitleTextArea;
    private JRadioButton expressTransferRadioButton;
    private JButton nextButton;
    private JButton cancelButton;
    private JLabel accountNumberWarning;
    private JLabel availableFundsLabel;
    private JLabel transferAmountWarning;
    private JLabel expressTransferWarning;
    private JLabel postcodeWarning;
    private JLabel transferTitleWarning;
    private JLabel receiverName1Warning;
    private JLabel receiverName2Warning;
    private JLabel townNameWarning;
    private JLabel streetNameWarning;
    private JLabel streetNumberWarning;
    private JTextField countryNameTxt;
    private JLabel countryNameLabel;
    private JLabel countryNameWarning;
    private JRadioButton receiverAddressRadioButton;
    private JLabel townNameLabel;
    private JLabel streetNameLabel;
    private JLabel postcodeLabel;
    private JLabel streetNumberLabel;
    private JLabel postcodeDashLabel;
    private JLabel streetNumberSlashLabel;
    private JLabel panelTitleLabel;
    private JLabel accountNumberCountryLabel;
    private JLabel transferAmountCurrencyLabel;
    private JLabel[] warnings = {accountNumberWarning, transferAmountWarning, expressTransferWarning, postcodeWarning, transferTitleWarning, receiverName1Warning, receiverName2Warning, townNameWarning, streetNameWarning, streetNumberWarning};
    boolean isCompany;
    boolean isPerson;
    boolean isAccountNumberValid;
    boolean isAmountValid;
    boolean isPostCodeValid;
    boolean isPayment;
    private KeyAdapter numbersOnly;
    private double transferAmount1 = 0.0;
    private double transferAmount2 = 0.0;
    private double finalTransferAmount = 0.0;
    private Map<String,String> receiverData;
    private Map<String,String> transferData;
    boolean buttonValid;
    private Vector<Boolean> validation;
    boolean isAddress;
    private Map<String,String> senderData;
    private double senderAmount;
    private MainFrame frame;

    public Transfer(MainFrame mainFrame, Map<String, String> senderData1, double senderAmount1) {
        frame = mainFrame;
        senderData = senderData1;
        senderAmount = senderAmount1;
        receiverData = new HashMap<>();
        transferData = new HashMap<>();
        numbersOnly = new OnlyNumbers().getKeyAdapter();

        setLabels();
        setReceiverNameCombo(receiverNameCombo);
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
        setPostcodeTxt(postcode1Txt);
        setPostcodeTxt(postcode2Txt);
        setAccountNumberTxt(accountNumberTxt);
        setExpressTransferRadioButton(expressTransferRadioButton,senderAmount1);
        setReceiverAddressRadioButton(receiverAddressRadioButton);
        setNextButton(nextButton);

        frame.getjFrame().setContentPane(transferPanel);
        frame.getjFrame().setVisible(true);
    }

    void setLabels(){
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

    void setReceiverNameCombo(JComboBox receiverNameCombo){
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
                receiverName1Label.setText("Nazwa firmy:");
                receiverName1Label.setVisible(true);
                receiverName1Txt.setVisible(true);
                receiverName2Warning.setVisible(false);
            } else if (c.getSelectedItem() == "Osoba") {
                isPerson = true;
                if (isCompany) {
                    receiverName1Label.setVisible(false);
                    receiverName1Txt.setVisible(false);
                }
                receiverName1Label.setText("Imię:");
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

    void setExpressTransferRadioButton(JRadioButton expressTransferRadioButton, double senderAmount) {
        expressTransferRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
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
                if(finalTransferAmount>senderAmount) validation.add(false);
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
                    receiverData.put("nr konta",String.valueOf(result));
                    receiverData.put("nazwa odbiorcy", receiverName1Txt.getText());
                    if(receiverNameCombo.getSelectedItem() == "Osoba") receiverData.put("nazwa odbiorcy cd", receiverName2Txt.getText());
                    else receiverData.put("nazwa odbiorcy cd","");
                    if(isAddress) {
                        receiverData.put("miejscowosc", townNameTxt.getText());
                        receiverData.put("kod pocztowy", postcode1Txt.getText() + "-" + postcode2Txt.getText());
                        receiverData.put("ulica", streetNameTxt.getText());
                        if (streetNumber2Txt.getText().length() > 0)
                            receiverData.put("nr domu", streetNumber1Txt.getText() + "/" + streetNumber2Txt.getText());
                        else receiverData.put("nr domu", streetNumber1Txt.getText());
                    }
                    transferData.put("tytul", transferTitleTextArea.getText());
                    transferData.put("kwota", transferAmount1Txt.getText()+"."+ transferAmount2Txt.getText());
                    if(isPayment){
                        transferData.put("oplata","5.00");
                        transferData.put("typ","Przelew krajowy natychmiastowy");
                    }
                    else{
                        transferData.put("oplata","0.00");
                        transferData.put("typ","Przelew krajowy zwykły");
                    }
                    transferData.put("waluta","PLN");
                    TransferNextStep pCd = new TransferNextStep(frame, transferPanel,senderData,receiverData, transferData,senderAmount);
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