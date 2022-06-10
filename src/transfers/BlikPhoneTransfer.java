package src.transfers;

import src.Database;
import src.MainScreen;
import src.Screen;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BlikPhoneTransfer implements Transfer{
    public JLabel panelTitleLabel;
    public JTextField phoneNumberTxt;
    public JComboBox receiverNameCombo;
    public JTextField receiverName1Txt;
    public JTextField receiverName2Txt;
    public JLabel receiverName1Label;
    public JTextField transferAmount1Txt;
    public JTextField transferAmount2Txt;
    public JLabel transferAmountWarning;
    public JTextArea transferTitleTextArea;
    public JLabel transferTitleWarning;
    public JButton cancelButton;
    public JButton nextButton;
    public JLabel availableFundsLabel;
    public JLabel phoneNumberWarning;
    public JLabel receiverName2Warning;
    public JLabel receiverName2Label;
    public JPanel blikPhonePanel;
    public JLabel receiverName1Warning;
    public JPanel timerPanel;
    public JLabel timeLabel;
    public MainFrame frame;
    protected Map<String,String> transferData;
    public double senderAmount;
    public KeyAdapter numbersOnly;
    protected JLabel[] warnings = {phoneNumberWarning, transferAmountWarning, transferTitleWarning, receiverName1Warning, receiverName2Warning};
    public boolean isCompany;
    public boolean isPerson;
    public boolean isAmountValid;
    public boolean isPhoneNumberValid;
    public double transferAmount1 = 0.0;
    public double transferAmount2 = 0.0;
    public double finalTransferAmount = 0.0;
    public Vector<Boolean> validation;
    public User user;
    public User receiver;
    public AccountChoosed accountChoosed;
    public String userAccountNumber;

    public BlikPhoneTransfer(AccountChoosed accountChoosed1, User user1, MainFrame mainFrame) {
        accountChoosed = accountChoosed1;
        user = user1;
        receiver = new User();
        if(accountChoosed==AccountChoosed.ORDINARYACCOUNT){
            userAccountNumber = user.ordinary_account_number;
            senderAmount = Math.round(user.ordinary_account_balance*100.0)/100.0;
        }
        else{
            userAccountNumber = user.savings_account_number;
            senderAmount = Math.round(user.savings_account_balance*100.0)/100.0;
        }
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        blikPhonePanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        transferData = new HashMap<>();
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        setCurrency();
        setLabels();
        setReceiverNameCombo();
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
        setPhoneNumberTxt();
        setNextButton();
        cancelButton.addActionListener(e->{
            frame.getjFrame().dispose();
            new MainScreen(user,null,new Screen()).CreateScreen();
        });
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
                if(!Database.verifyPhoneNumber(phoneNumberTxt.getText())){
                    validation.add(false);
                    phoneNumberWarning.setText("Nie ma takiego numeru telefonu");
                    phoneNumberWarning.setVisible(true);
                }
                else{
                    validation.add(true);
                    phoneNumberWarning.setVisible(false);
                    String userName = Database.getUserByPhone(phoneNumberTxt.getText());
                    receiver.phone_number = phoneNumberTxt.getText();
                    receiver.ordinary_account_number = Database.getOrdinaryAccountNumber(userName);
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
                    receiver.firstName =  receiverName1Txt.getText();
                    if(receiverNameCombo.getSelectedItem() == "Osoba") receiver.lastName = receiverName2Txt.getText();
                    else receiver.lastName = "";
                    transferData.put("tytul", transferTitleTextArea.getText());
                    transferData.put("kwota", transferAmount1Txt.getText()+"."+ transferAmount2Txt.getText());
                    transferData.put("oplata","0.00");
                    transferData.put("typ",panelTitleLabel.getText());
                    TransferNextStep pCd = new TransferNextStep(accountChoosed,user,AccountChoosed.ORDINARYACCOUNT,receiver,transferData,frame, blikPhonePanel);
                    frame.getjFrame().setContentPane(pCd.getTransferNextStepPanel());
                    frame.getjFrame().setVisible(true);
                }
            }
        });
    }
}

