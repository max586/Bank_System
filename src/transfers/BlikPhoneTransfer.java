package transfers;

import mainFrame.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BlikPhoneTransfer implements Transfer{
    private JLabel panelTitleLabel;
    private JTextField phoneNumberTxt;
    private JComboBox receiverNameCombo;
    private JTextField receiverName1Txt;
    private JTextField receiverName2Txt;
    private JLabel receiverName1Label;
    private JTextField transferAmount1Txt;
    private JTextField transferAmount2Txt;
    private JLabel transferAmountWarning;
    private JTextArea transferTitleTextArea;
    private JLabel transferTitleWarning;
    private JButton cancelButton;
    private JButton nextButton;
    private JLabel availableFundsLabel;
    private JLabel phoneNumberWarning;
    private JLabel receiverName2Warning;
    private JLabel receiverName2Label;
    private JPanel blikPhonePanel;
    private JLabel receiverName1Warning;
    private MainFrame frame;
    private Map<String,String> senderData;
    protected Map<String,String> receiverData;
    protected Map<String,String> transferData;
    private double senderAmount;
    private KeyAdapter numbersOnly;
    protected JLabel[] warnings = {phoneNumberWarning, transferAmountWarning, transferTitleWarning, receiverName1Warning, receiverName2Warning};
    private boolean isCompany;
    private boolean isPerson;
    private boolean isAmountValid;
    private boolean isPhoneNumberValid;
    private double transferAmount1 = 0.0;
    private double transferAmount2 = 0.0;
    private double finalTransferAmount = 0.0;
    private Vector<Boolean> validation;


    public BlikPhoneTransfer(MainFrame mainFrame, Map<String, String> senderData1) {
        frame = mainFrame;
        senderData = senderData1;
        senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
        receiverData = new HashMap<>();
        transferData = new HashMap<>();
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        setCurrency();
        setLabels();
        setReceiverNameCombo();
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
        setPhoneNumberTxt();
        setNextButton();
        frame.getjFrame().setContentPane(blikPhonePanel);
        frame.getjFrame().setVisible(true);
    }
    void setLabels(){
        availableFundsLabel.setText(String.valueOf(senderAmount));
        for(JLabel warning: warnings) warning.setVisible(false);
        receiverName1Label.setVisible(false);
        receiverName1Txt.setVisible(false);
        receiverName2Label.setVisible(false);
        receiverName2Txt.setVisible(false);
    }
    void setCurrency(){
        transferData.put("waluta","PLN");
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
    void setPhoneNumberTxt(){
        phoneNumberTxt.setDocument(new LimitJTextField(9));
        phoneNumberTxt.addKeyListener(numbersOnly);
        phoneNumberTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(phoneNumberTxt.getText().length()<8){
                    isPhoneNumberValid = false;
                }
                else {
                    isPhoneNumberValid = e.getKeyChar() != KeyEvent.VK_BACK_SPACE;
                }
                phoneNumberWarning.setText("Niepoprawny numer telefonu");
                phoneNumberWarning.setVisible(!isPhoneNumberValid);
            }
        });
    }
    void setNextButton(){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                if(phoneNumberTxt.getText().length()==0){
                    phoneNumberWarning.setText("To pole jest wymagane");
                    phoneNumberWarning.setVisible(true);
                    validation.add(false);
                }
                else {
                    phoneNumberWarning.setVisible(false);
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
                if(!isPhoneNumberValid) validation.add(false);
                if(!validation.contains(false)){
                    String phoneNumber = phoneNumberTxt.getText();
                    StringBuilder result = new StringBuilder();
                    for(int i=0; i<phoneNumber.length();++i){
                        result.append(phoneNumber.charAt(i));
                        if((i+1)%3==0) result.append(" ");
                    }
                    receiverData.put("nr telefonu",String.valueOf(result));
                    receiverData.put("nazwa odbiorcy", receiverName1Txt.getText());
                    if(receiverNameCombo.getSelectedItem() == "Osoba") receiverData.put("nazwa odbiorcy cd", receiverName2Txt.getText());
                    else receiverData.put("nazwa odbiorcy cd","");
                    transferData.put("tytul", transferTitleTextArea.getText());
                    transferData.put("kwota", transferAmount1Txt.getText()+"."+ transferAmount2Txt.getText());
                    transferData.put("oplata","0.00");
                    transferData.put("typ",panelTitleLabel.getText());
                    TransferNextStep pCd = new TransferNextStep(frame, blikPhonePanel,senderData,receiverData, transferData);
                    frame.getjFrame().setContentPane(pCd.getTransferNextStepPanel());
                    frame.getjFrame().setVisible(true);
                }
            }
        });
    }
}
