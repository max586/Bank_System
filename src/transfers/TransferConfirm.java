package src.transfers;

import src.mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

    public TransferConfirm(MainFrame mainFrame, JPanel transferNextStepPanel, Map<String,String> senderData1, Map<String,String>receiverData1, Map<String,String> transferData1){
        frame = mainFrame;
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
                        appCodeWarning.setVisible(false);
                        if(isTransferConfirmation){
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String generationDate = dtf.format(now);
                            if(transferPanelTitle.equals("Zlecenie stałe")) pdfGenerator = new PdfGeneratorStandingOrder(generationDate,senderData,receiverData,transferData);
                            else if(transferPanelTitle.equals("Przelew BLIK na telefon")) pdfGenerator = new PdfGeneratorBLIK(generationDate,senderData,receiverData,transferData);
                            else if(transferPanelTitle.equals("Przelew własny")) pdfGenerator = new PdfGeneratorOwn(generationDate,senderData,receiverData,transferData);
                            else pdfGenerator = new PdfGeneratorStandard(generationDate,senderData,receiverData,transferData);
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
