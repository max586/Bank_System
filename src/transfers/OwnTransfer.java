package transfers;

import mainFrame.MainFrame;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class OwnTransfer implements Transfer{
    private MainFrame frame;
    private JPanel OwnTransferPanel;
    private JLabel panelTitleLabel;
    private JMenuBar chooseAccountMenuBar;
    private JTextField transferAmount1Txt;
    private JTextField transferAmount2Txt;
    private JLabel transferAmountWarning;
    private JTextArea transferTitleTextArea;
    private JLabel transferTitleWarning;
    private JButton cancelButton;
    private JButton nextButton;
    private JLabel receiverInfoLabel;
    private JLabel receiverLabel;
    private JLabel senderAccountWarning;
    private Font customFont;
    private JMenu jMenu;
    private boolean isMainAccountSelected;
    private boolean isSavingAccountSelected;
    private boolean isAmountValid;
    private double transferAmount1 = 0.0;
    private double transferAmount2 = 0.0;
    private double finalTransferAmount = 0.0;
    private double senderAmount = 0.0;
    private KeyAdapter numbersOnly;
    private Map<String,String> senderData;
    private Map<String,String> receiverData;
    private Map<String,String> transferData;
    private Vector<Boolean> validation;
    private String choosedAcount="";

    public OwnTransfer(MainFrame mainFrame, Map<String,String> senderData1) {
        try {
            setFonts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        senderData = senderData1;
        receiverData = new HashMap<>();
        transferData = new HashMap<>();
        transferData.put("waluta","PLN");
        frame = mainFrame;
        setLabels();
        setjMenu();
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
        setNextButton();
        frame.getjFrame().setContentPane(OwnTransferPanel);
        frame.getjFrame().setVisible(true);

    }

    void setjMenu(){
        jMenu = new JMenu("Wybierz konto");
        JMenuItem j0 = new JMenuItem("Wybierz konto");
        j0.setFont(customFont);
        MenuInfo.m1 = new JMenu("Konto główne");
        MenuInfo.m2 = new JMenu("Konto oszczędnościowe");
        MenuInfo.m1.setPreferredSize(new Dimension(350,100));
        MenuInfo.m2.setPreferredSize(new Dimension(350,100));
        MenuInfo.accountNumber1 = senderData.get("nrkonta1");
        MenuInfo.accountAmount1 = senderData.get("konto1srodki")+" "+"PLN";
        MenuInfo.accountNumber2 = senderData.get("nrkonta2");
        MenuInfo.accountAmount2 = senderData.get("konto2srodki")+" "+"PLN";
        MenuInfo.previnfo1 = MenuInfo.m1.getText();
        MenuInfo.previnfo2 = MenuInfo.m2.getText();
        jMenu.setFont(customFont);
        j0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JMenuItem menuItem = (JMenuItem)e.getSource();
                jMenu.setText(menuItem.getText());
                jMenu.setFont(customFont);
                isMainAccountSelected = false;
                isSavingAccountSelected = false;
                receiverLabel.setVisible(false);
                receiverInfoLabel.setVisible(false);
                senderAccountWarning.setVisible(true);
                senderAmount = 0.0;
                choosedAcount = "";
            }
        });

        MenuInfo.m1.setFont(customFont);
        MenuInfo.m2.setFont(customFont);
        MenuInfo.m1.addMenuListener(new SampleMenuListenerAccount1());
        MenuInfo.m2.addMenuListener(new SampleMenuListenerAccount2());
        MenuInfo.m1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jMenu.setText(MenuInfo.m1.getText());
                jMenu.setFont(MenuInfo.font3);
                isMainAccountSelected = true;
                isSavingAccountSelected = false;
                receiverLabel.setVisible(true);
                receiverInfoLabel.setText("<html>"+MenuInfo.previnfo2+"<br>"+MenuInfo.accountAmount2 +"<br>"+MenuInfo.accountNumber2+"</html>");
                receiverInfoLabel.setVisible(true);
                senderAccountWarning.setVisible(false);
                senderAmount = Double.parseDouble(senderData.get("konto1srodki"));
                choosedAcount = MenuInfo.accountNumber2;
            }
        });
        MenuInfo.m2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jMenu.setText(MenuInfo.m2.getText());
                jMenu.setFont(MenuInfo.font3);
                isSavingAccountSelected = true;
                isMainAccountSelected = false;
                receiverLabel.setVisible(true);
                receiverInfoLabel.setText("<html>"+MenuInfo.previnfo1+"<br>"+MenuInfo.accountAmount1 +"<br>"+MenuInfo.accountNumber1+"</html>");
                receiverInfoLabel.setVisible(true);
                senderAccountWarning.setVisible(false);
                senderAmount = Double.parseDouble(senderData.get("konto2srodki"));
                choosedAcount = MenuInfo.accountNumber1;
            }
        });
        jMenu.add(j0);
        jMenu.add(MenuInfo.m1);
        jMenu.add(MenuInfo.m2);
        jMenu.setMnemonic(KeyEvent.VK_T);
        chooseAccountMenuBar.add(jMenu);
    }

    void setFonts() throws IOException, FontFormatException {
        customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")).deriveFont(18f);
        MenuInfo.font1 = Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")).deriveFont(18f);
        MenuInfo.font2 = Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")).deriveFont(Font.BOLD,14f);
        MenuInfo.font3 = Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")).deriveFont(Font.ITALIC,14f);
    }

    void setLabels(){
        receiverLabel.setVisible(false);
        receiverInfoLabel.setVisible(false);
        senderAccountWarning.setVisible(false);
        transferAmountWarning.setVisible(false);
        transferTitleWarning.setVisible(false);
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

    void setNextButton(){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                if(!isMainAccountSelected && !isSavingAccountSelected){
                    senderAccountWarning.setVisible(true);
                    validation.add(false);
                }
                else{
                    senderAccountWarning.setVisible(false);
                    validation.add(true);
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
                if(!validation.contains(false)){
                    receiverData.put("nr konta",choosedAcount);
                    receiverData.put("nazwa odbiorcy", senderData.get("nazwa odbiorcy"));
                    receiverData.put("nazwa odbiorcy cd",senderData.get("nazwa odbiorcy cd"));
                    transferData.put("tytul", transferTitleTextArea.getText());
                    transferData.put("kwota", transferAmount1Txt.getText()+"."+ transferAmount2Txt.getText());
                    transferData.put("oplata","0.00");
                    transferData.put("typ",panelTitleLabel.getText());
                    TransferNextStep pCd = new TransferNextStep(frame, OwnTransferPanel,senderData,receiverData, transferData);
                    frame.getjFrame().setContentPane(pCd.getTransferNextStepPanel());
                    frame.getjFrame().setVisible(true);
                }
            }
        });
    }
}


class MenuInfo{
    public static JMenu m1;
    public static JMenu m2;
    public static String previnfo1;
    public static String accountNumber1;
    public static String accountAmount1;
    public static String previnfo2;
    public static String accountNumber2;
    public static String accountAmount2;
    public static Font font1;
    public static Font font2;
    public static Font font3;
}

class SampleMenuListenerAccount1 implements MenuListener {
    SampleMenuListenerAccount1(){}
    @Override
    public void menuSelected(MenuEvent e) {
        MenuInfo.m1.setFont(MenuInfo.font3);
        MenuInfo.m1.setText("<html>"+MenuInfo.previnfo1+"<br>"+MenuInfo.accountAmount1 +"<br>"+MenuInfo.accountNumber1+"</html>");

    }

    @Override
    public void menuDeselected(MenuEvent e) {
        MenuInfo.m1.setFont(MenuInfo.font1);
        MenuInfo.m1.setText(MenuInfo.previnfo1);
    }

    @Override
    public void menuCanceled(MenuEvent e) {}
}

class SampleMenuListenerAccount2 implements MenuListener {
    SampleMenuListenerAccount2(){}
    @Override
    public void menuSelected(MenuEvent e) {
        MenuInfo.m2.setFont(MenuInfo.font3);
        MenuInfo.m2.setText("<html>"+MenuInfo.previnfo2+"<br>"+MenuInfo.accountAmount2 +"<br>"+MenuInfo.accountNumber2+"</html>");
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        MenuInfo.m2.setFont(MenuInfo.font1);
        MenuInfo.m2.setText(MenuInfo.previnfo2);
    }

    @Override
    public void menuCanceled(MenuEvent e) {}
}