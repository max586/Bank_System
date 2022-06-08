package src.transfers;
import src.Database;
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
    public Vector<JTextField> appCode;
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
    public Map<String,String> senderData;
    public Map<String,String> receiverData;
    public Map<String,String> transferData;
    public PdfGenerator pdfGenerator;
    public Statement st;
    public User user;
    public TransferConfirm(User user1,MainFrame mainFrame, JPanel transferNextStepPanel, Map<String,String> senderData1, Map<String,String>receiverData1, Map<String,String> transferData1){
        user = user1;
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        transferConfirmPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        otherPanel = transferNextStepPanel;
        senderData=senderData1;
        receiverData = receiverData1;
        transferData = transferData1;
        if(transferData.get("typ").equals("Przelew BLIK na telefon")) transferPanelTitle = transferData.get("typ");
        else {
            String[] arr = transferData.get("typ").split("\\s+");
            transferPanelTitle = arr[0] + " " + arr[1];
        }
        transferPanelTitleLabel.setText(transferPanelTitle);
        fileScrollPane.setVisible(false);
        chooseFolderLabel.setVisible(false);
        fileTree.setModel(new FileSystem());
        appCodeWarning.setVisible(false);
        isTransferConfirmation = false;
        confirmButtonValid = true;
        appCodeStr = String.valueOf(senderData.get("kod"));
        appCode = new Vector<>();
        appCode.add(appCodeTxt1);
        appCode.add(appCodeTxt2);
        appCode.add(appCodeTxt3);
        appCode.add(appCodeTxt4);
        numbersOnly = new OnlyNumbers().getKeyAdapter();

        for(JTextField t: appCode){
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
                for(JTextField t: appCode){
                    if(t.getText().length()==0){
                        confirmButtonValidation.add(false);
                    }
                    else confirmButtonValidation.add(true);
                }
                confirmButtonValid = !confirmButtonValidation.contains(false);
                if(!confirmButtonValid){
                    appCodeWarning.setText("Podaj czterocyfrowy kod");
                    appCodeWarning.setVisible(true);
                }
                else{
                    appCodeWarning.setVisible(false);
                    appCodeWarning.setText("Podany kod jest nieprawidłowy");
                    StringBuilder podanyKod = new StringBuilder();
                    for(JTextField t: appCode) podanyKod.append(t.getText());
                    if(!appCodeStr.equals(String.valueOf(podanyKod))){
                        appCodeWarning.setVisible(true);
                    }
                    else {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String generationDate = dtf.format(now);
                        String town, postCode,street,streetNumber;
                        town = receiverData.getOrDefault("miejscowosc", "");
                        postCode = receiverData.getOrDefault("kod pocztowy","");
                        street = receiverData.getOrDefault("ulica","");
                        streetNumber = receiverData.getOrDefault("nr domu","");
                        appCodeWarning.setVisible(false);
                        if(transferPanelTitle.equals("Zlecenie stałe")){
                            Database.addToHistory(st,"OutgoingHistoryOrdinary",generationDate,transferData.get("typ"),
                                    senderData.get("nr konta"),receiverData.get("nr konta"),"",Double.parseDouble(transferData.get("kwota")),
                                    transferData.get("waluta"),Double.parseDouble(transferData.get("kwotaPLN")),transferData.get("tytul"),
                                    transferData.get("startdata"),transferData.get("enddata"),Integer.parseInt(transferData.get("cykle")),
                                    transferData.get("jednostkaczasu"),receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    town,postCode,street,streetNumber);

                        }
                        else if(transferPanelTitle.equals("Przelew BLIK na telefon")){
                            Database.addToHistory(st,"OutgoingHistoryOrdinary",generationDate,transferData.get("typ"),
                                    senderData.get("nr konta"),"",receiverData.get("nr telefonu"),Double.parseDouble(transferData.get("kwota")),
                                    "PLN",Double.parseDouble(transferData.get("kwota")),transferData.get("tytul"),
                                    "","",0,"",receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    "","","","");
                        }
                        else if(transferPanelTitle.equals("Przelew własny")){
                            if(senderData.get("nr konta").equals(user.ordinary_account_number)) {
                                System.out.println("a");
                                Database.addToHistory(st, "OutgoingHistoryOrdinary", generationDate, transferData.get("typ"),
                                        senderData.get("nr konta"), receiverData.get("nr konta"), "", Double.parseDouble(transferData.get("kwota")),
                                        "PLN", Double.parseDouble(transferData.get("kwota")), transferData.get("tytul"),
                                        "", "", 0, "", receiverData.get("nazwa odbiorcy"), receiverData.get("nazwa odbiorcy cd"),
                                        "", "", "", "");
                            }
                            else if(senderData.get("nr konta").equals(user.savings_account_number)){
                                System.out.println("b");
                                Database.addToHistory(st, "OutgoingHistorySavings", generationDate, transferData.get("typ"),
                                        senderData.get("nr konta"), "", receiverData.get("nr telefonu"), Double.parseDouble(transferData.get("kwota")),
                                        "PLN", Double.parseDouble(transferData.get("kwota")), transferData.get("tytul"),
                                        "", "", 0, "", receiverData.get("nazwa odbiorcy"), receiverData.get("nazwa odbiorcy cd"),
                                        "", "", "", "");
                            }
                        }
                        else {
                            Database.addToHistory(st,"OutgoingHistoryOrdinary",generationDate,transferData.get("typ"),
                                    senderData.get("nr konta"),receiverData.get("nr konta"),"",Double.parseDouble(transferData.get("kwota")),
                                    transferData.get("waluta"),Double.parseDouble(transferData.get("kwotaPLN")),transferData.get("tytul"),
                                    "","",0,"",receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    town,postCode,street,streetNumber);
                        }
                        if(isTransferConfirmation){
                            if(transferPanelTitle.equals("Zlecenie stałe")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.ZLECENIESTALE);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("Przelew BLIK na telefon")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.BLIK);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("Przelew własny")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.WLASNY);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.STANDARD);
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
                    }
                }
            }
        });
    }

    JPanel getTransferConfirmPanel(){
        return transferConfirmPanel;
    }
}
