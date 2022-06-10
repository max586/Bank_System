package src.transfers;
import src.Database;
import src.MainScreen;
import src.Screen;
import src.User;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Vector;

public class TransferConfirm {
    public JTextField appCodeTxt1;
    public JTextField appCodeTxt2;
    public JTextField appCodeTxt3;
    public JTextField appCodeTxt4;
    public Vector<JTextField> applicationCode;
    public JLabel appCodeWarning;
    public JButton cancelButton;
    public JButton confirmTrasferButton;
    public JRadioButton printTransferConfirmationRadioButton;
    public JPanel transferConfirmPanel;
    public JLabel chooseFolderLabel;
    public JTree fileTree;
    public JScrollPane fileScrollPane;
    public String transferPanelTitle;
    public JLabel transferPanelTitleLabel;
    public JLabel timeLabel;
    public JPanel timerPanel;
    public KeyAdapter numbersOnly;
    public boolean isTransferConfirmation;
    public boolean confirmButtonValid;
    public Vector<Boolean> confirmButtonValidation;
    public String appCodeStr;
    public String path;
    public MainFrame frame;
    public JPanel otherPanel;
    public Map<String,String> transferData;
    public PdfGenerator pdfGenerator;
    public User user;
    public User receiver;
    public String userAccountNumber;
    public double userAccountBalance;
    public AccountChoosed accountChoosedUser;
    public AccountChoosed accountChoosedReceiver;
    public String receiverAccountNr;
    public TransferConfirm(AccountChoosed accountChoosed1, User user1, AccountChoosed accountChoosed2, User receiver1, Map<String,String> transferData1, MainFrame mainFrame, JPanel transferNextStepPanel){
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
        if(receiver.ordinary_account_number.length()==28) receiverAccountNr = receiver.ordinary_account_number;
        else receiverAccountNr = receiver.savings_account_number;
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        transferConfirmPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        otherPanel = transferNextStepPanel;
        transferData = transferData1;
        if(transferData.get("type").equals("BLIK Phone Transfer")) transferPanelTitle = transferData.get("type");
        else {
            String[] arr = transferData.get("type").split("\\s+");
            transferPanelTitle = arr[0] + " " + arr[1];
        }
        transferPanelTitleLabel.setText(transferPanelTitle);
        fileScrollPane.setVisible(false);
        chooseFolderLabel.setVisible(false);
        fileTree.setModel(new FileSystem());
        appCodeWarning.setVisible(false);
        isTransferConfirmation = false;
        confirmButtonValid = true;
        appCodeStr = user.appCode;
        applicationCode = new Vector<>();
        applicationCode.add(appCodeTxt1);
        applicationCode.add(appCodeTxt2);
        applicationCode.add(appCodeTxt3);
        applicationCode.add(appCodeTxt4);
        numbersOnly = new OnlyNumbers().getKeyAdapter();

        for(JTextField t: applicationCode){
            t.setDocument(new LimitJTextField(1));
            t.addKeyListener(numbersOnly);
        }
        setCancelButton(cancelButton);
        setPrintTransferConfirmationRadioButton(printTransferConfirmationRadioButton);
        fileTree.setEditable(true);
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                File file = (File) fileTree.getLastSelectedPathComponent();
                if (file == null) path = "";
                else path = file.getPath();
            }
        });
        setConfirmTrasferButton(confirmTrasferButton);
    }

    void setPrintTransferConfirmationRadioButton(JRadioButton printTransferConfirmationRadioButton){
        printTransferConfirmationRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    fileScrollPane.setVisible(true);
                    chooseFolderLabel.setVisible(true);
                    isTransferConfirmation = true;
                } else if (state == ItemEvent.DESELECTED) {
                    fileScrollPane.setVisible(false);
                    chooseFolderLabel.setVisible(false);
                    isTransferConfirmation = false;
                }
            }
        });
    }

    void setCancelButton(JButton cancelButton){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(otherPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }

    void setConfirmTrasferButton(JButton confirmTrasferButton){
        confirmTrasferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButtonValidation = new Vector<Boolean>();
                for(JTextField t: applicationCode){
                    if(t.getText().length()==0){
                        confirmButtonValidation.add(false);
                    }
                    else confirmButtonValidation.add(true);
                }
                confirmButtonValid = !confirmButtonValidation.contains(false);
                if(!confirmButtonValid){
                    appCodeWarning.setText("This field is required");
                    appCodeWarning.setVisible(true);
                }
                else{
                    appCodeWarning.setVisible(false);
                    appCodeWarning.setText("Invalid App Code");
                    StringBuilder podanyKod = new StringBuilder();
                    for(JTextField t: applicationCode) podanyKod.append(t.getText());
                    if(!appCodeStr.equals(String.valueOf(podanyKod))){
                        appCodeWarning.setVisible(true);
                    }
                    else {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String generationDate = dtf.format(now);
                        String town, postCode,street,streetNumber;
                        if(receiver.city!=null) {
                            town = receiver.city;
                            postCode = receiver.post_code;
                            street = receiver.street;
                            streetNumber = receiver.street_nr;
                        }
                        else {town="";postCode="";street="";streetNumber="";}
                        appCodeWarning.setVisible(false);
                        if(transferPanelTitle.equals("Standing Order")){
                            if(accountChoosedUser==AccountChoosed.ORDINARYACCOUNT) {
                                Database.addToHistory("HistoryOrdinary", generationDate, transferData.get("type"),
                                        userAccountNumber, receiverAccountNr, "" ,Double.parseDouble(transferData.get("transferamount")),
                                        transferData.get("currency"), Double.parseDouble(transferData.get("kwotaPLN")), transferData.get("title"),
                                        transferData.get("startdate"), transferData.get("enddate"), Integer.parseInt(transferData.get("cicles")),
                                        transferData.get("timeunit"),receiver.firstName,receiver.lastName,receiver.city,receiver.post_code,receiver.street,receiver.street_nr);
                                setBalanceSender(user,AccountChoosed.ORDINARYACCOUNT);
                                setBalanceReceiver();
                            }
                        }
                        else if(transferPanelTitle.equals("BLIK Phone Transfer")){
                            if(accountChoosedUser==AccountChoosed.ORDINARYACCOUNT) {
                                Database.addToHistory("HistoryOrdinary", generationDate, transferData.get("type"),
                                        userAccountNumber, "",user.phone_number, Double.parseDouble(transferData.get("transferamount")),
                                        "PLN", Double.parseDouble(transferData.get("transferamount")), transferData.get("title"),
                                        "", "", 0,"","","","","","","");
                                setBalanceSender(user,AccountChoosed.ORDINARYACCOUNT);
                                setBalanceReceiver();
                            }
                        }
                        else if(transferPanelTitle.equals("Own Transfer")){
                            if(accountChoosedUser==AccountChoosed.ORDINARYACCOUNT) {
                                Database.addToHistory( "HistoryOrdinary", generationDate, transferData.get("type"),
                                        userAccountNumber, receiverAccountNr,"",  Double.parseDouble(transferData.get("transferamount")),
                                        "PLN", Double.parseDouble(transferData.get("transferamount")), transferData.get("title"),
                                        "", "", 0, "","","","","","","");
                                setBalanceSender(user,AccountChoosed.ORDINARYACCOUNT);
                                setBalanceReceiver();
                            }
                            else{
                                Database.addToHistory( "HistorySavings", generationDate, transferData.get("type"),
                                        userAccountNumber, receiverAccountNr,"",  Double.parseDouble(transferData.get("transferamount")),
                                        "PLN", Double.parseDouble(transferData.get("transferamount")), transferData.get("title"),
                                        "", "", 0, "","","","","","","");
                                setBalanceSender(user,AccountChoosed.SAVINGSACCOUNT);
                                setBalanceReceiver();
                            }
                        }
                        else {
                            if(accountChoosedUser==AccountChoosed.ORDINARYACCOUNT) {
                                Database.addToHistory("HistoryOrdinary", generationDate, transferData.get("type"),
                                        userAccountNumber, receiverAccountNr, "", Double.parseDouble(transferData.get("transferamount")),
                                        transferData.get("currency"), Double.parseDouble(transferData.get("totaltransferamount")), transferData.get("title"),
                                        "", "", 0, "",receiver.firstName,receiver.lastName,receiver.city,receiver.post_code,receiver.street,receiver.street_nr);
                                setBalanceSender(user,AccountChoosed.ORDINARYACCOUNT);
                                setBalanceReceiver();
                            }
                        }
                        ;
                        if(isTransferConfirmation){
                            if(transferPanelTitle.equals("Standing Order")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,user,accountChoosedUser,receiver,accountChoosedReceiver,transferData).getPdfGenerator(PdfFactory.PdfType.ZLECENIESTALE);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("BLIK Phone Transfer")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,user,accountChoosedUser,receiver,accountChoosedReceiver,transferData).getPdfGenerator(PdfFactory.PdfType.BLIK);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("Own Transfer")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,user,accountChoosedUser,receiver,accountChoosedReceiver,transferData).getPdfGenerator(PdfFactory.PdfType.WLASNY);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,user,accountChoosedUser,receiver,accountChoosedReceiver,transferData).getPdfGenerator(PdfFactory.PdfType.STANDARD);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            try {
                                pdfGenerator.generatePDF(path);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        frame.getjFrame().dispose();
                        new MainScreen(user,null,new Screen()).CreateScreen();
                    }
                }
            }
        });
    }
    void setBalanceSender(User user1, AccountChoosed accountChoosed){
        if(accountChoosed==AccountChoosed.ORDINARYACCOUNT) {
            float senderAmount = user1.ordinary_account_balance;
            float transferAmount = (float) Double.parseDouble(transferData.get("totaltransferamount"));
            senderAmount = senderAmount - transferAmount;
            Database.setOrdinaryAccountBalance(user1.username, senderAmount);
        }
        else{
            float senderAmount = user1.savings_account_balance;
            float transferAmount = (float) Double.parseDouble(transferData.get("totaltransferamount"));
            senderAmount = senderAmount - transferAmount;
            Database.setSavingsAccountBalance(user1.username, senderAmount);
        }
    }

    void setBalanceReceiver(){
        if(Database.isAccountNumberOrdinary(receiverAccountNr)){
            String receiverUserName = Database.getUserNameByAccount(receiverAccountNr,"Ordinary");
            float receiverAmount = Database.getOrdinaryAccountBalance(receiverUserName);
            float transferAmount = (float) Double.parseDouble(transferData.get("totaltransferamount"));
            receiverAmount = receiverAmount + transferAmount;
            Database.setOrdinaryAccountBalance(receiverUserName, receiverAmount);
        }
        else{
            String receiverUserName = Database.getUserNameByAccount(receiverAccountNr,"Savings");
            float receiverAmount = Database.getSavingsAccountBalance(receiverUserName);
            float transferAmount = (float) Double.parseDouble(transferData.get("totaltransferamount"));
            receiverAmount = receiverAmount + transferAmount;
            Database.setSavingsAccountBalance(receiverUserName, receiverAmount);
        }
    }

    JPanel getTransferConfirmPanel(){
        return transferConfirmPanel;
    }
}
