package src.transfers;

import com.toedter.calendar.JDateChooser;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

public class StandingOrderNextStep {
    private MainFrame frame;
    private JPanel cancelPanel;
    private JLabel panelTitleLabel;
    private JComboBox timeUnitsComboBox;
    private JTextField timeUnitsTxt;
    private JPanel StandingOrderNextPanel;
    private JLabel startPaymentLabel;
    private JPanel startPaymentPanel;
    private JPanel endPaymentPanel;
    private JButton cancelButton;
    private JButton nextButton;
    private JLabel startPaymentWarning;
    private JRadioButton endDateRadioButton;
    private JLabel endPaymentLabel;
    private JLabel endPaymentWarning;
    private JLabel timeUnitsWarning;
    private JLabel timeUnitsWarning2;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JDateChooser dateChooserFrom;
    private JDateChooser dateChooserTo;
    private boolean isEndDateSelected;
    private Date dateFrom;
    private Date localDate;
    private Date dateTo;
    private LocalDateTime timeAmount;
    private boolean dateFromValid;
    private Font customFont;
    private KeyAdapter numbersOnly;
    private Vector<Boolean> validation;
    private boolean timeUnitsValid;
    private String timeUnit;
    private boolean dateToValid;
    private Map<String,String> senderData;
    private Map<String,String> receiverData;
    private Map<String,String> transferData;
    private double senderAmount;
    private User user;
    public StandingOrderNextStep(User user1, MainFrame mainFrame, JPanel standingOrderPanel, Map<String,String> senderData1, Map<String,String> receiverData1, Map<String,String> transferData1) throws IOException, FontFormatException {
        user = user1;
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        StandingOrderNextPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        senderData = senderData1;
        receiverData = receiverData1;
        transferData = transferData1;
        senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
        cancelPanel = standingOrderPanel;
        timeUnitsValid = false;
        setLabels();
        dateFromValid = true;
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Futura.ttc")).deriveFont(12f);
        localDate = new Date();
        dateFrom = localDate;
        dateTo = localDate;
        setTimeUnitsComboBox();
        setStartPaymentPanel();
        setEndDateRadioButton();
        setEndPaymentPanel();
        setNextButton();
        setCancelButton();
    }
    void setLabels(){
        timeUnitsTxt.addKeyListener(numbersOnly);
        timeUnitsWarning.setVisible(false);
        timeUnitsWarning2.setVisible(false);
        endPaymentWarning.setVisible(false);
        startPaymentWarning.setVisible(false);
    }
    void setNextButton(){
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                if(!dateFromValid) validation.add(false);
                else validation.add(true);
                if(!timeUnitsValid){
                    validation.add(false);
                    timeUnitsWarning2.setVisible(true);
                }
                else{
                    validation.add(true);
                    timeUnitsWarning2.setVisible(false);
                }
                if(timeUnitsTxt.getText().length()==0){
                    validation.add(false);
                    timeUnitsWarning.setVisible(true);
                }
                else{
                    validation.add(true);
                    timeUnitsWarning.setVisible(false);
                }
                if(!validation.contains(false)) {
                    switch (timeUnit) {
                        case "dni" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusDays(Integer.parseInt(timeUnitsTxt.getText()));
                        case "tygodnie" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusWeeks(Integer.parseInt(timeUnitsTxt.getText()));
                        case "miesiące" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusMonths(Integer.parseInt(timeUnitsTxt.getText()));
                    }
                    if(isEndDateSelected) {
                        if(!dateToValid){
                            validation.add(false);
                        }
                        else {
                            validation.add(true);
                        }
                        if (dateTo.before(java.util.Date.from(timeAmount.atZone(ZoneId.systemDefault())
                                .toInstant()))) {
                            endPaymentWarning.setText("Zbyt krótki odstęp czasu!");
                            endPaymentWarning.setVisible(true);
                            validation.add(false);
                        } else {
                            endPaymentWarning.setVisible(false);
                            validation.add(true);
                        }
                    }
                    if(!validation.contains(false)){
                        transferData.replace("typ","Zlecenie stałe "+"co "+timeUnitsTxt.getText()+" "+timeUnit);
                        transferData.put("cykle",timeUnitsTxt.getText());
                        transferData.put("jednostkaczasu",timeUnit);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        transferData.put("startdata",formatter.format(dateChooserFrom.getDate()));
                        if(isEndDateSelected) transferData.put("enddata",formatter.format(dateChooserTo.getDate()));
                        TransferNextStep transferNextStep = new TransferNextStep(user, frame, StandingOrderNextPanel,senderData,receiverData,transferData);
                        frame.getjFrame().setContentPane(transferNextStep.getTransferNextStepPanel());
                        frame.getjFrame().setVisible(true);
                    }
                }
            }
        });
    }
    void setTimeUnitsComboBox(){
        timeUnitsComboBox.addActionListener(event -> {
            JComboBox c = (JComboBox) event.getSource();
            if (c.getSelectedItem() == "dzień") {
                timeUnit = "dni";
                timeUnitsValid = true;
                timeUnitsWarning2.setVisible(false);
            }
            else if(c.getSelectedItem()=="tydzień"){
                timeUnit = "tygodnie";
                timeUnitsValid=true;
                timeUnitsWarning2.setVisible(false);
            }
            else if(c.getSelectedItem()=="miesiąc"){
                timeUnit = "miesiące";
                timeUnitsValid=true;
                timeUnitsWarning2.setVisible(false);
            }
            else{
                timeUnit = "";
                timeUnitsValid=false;
                timeUnitsWarning2.setVisible(true);
            }
        });
    }
    void setStartPaymentPanel(){
        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setFont(customFont);
        dateChooserFrom.setDate(localDate);
        dateChooserFrom.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            dateFrom = (Date) e.getNewValue();
                            if(dateFrom.before(localDate)){
                                dateFromValid = false;
                                startPaymentWarning.setText("Data nie może być wcześniejsza od dzisiejszej!");
                                startPaymentWarning.setVisible(true);
                            }
                            else{
                                dateFromValid = true;
                                startPaymentWarning.setVisible(false);
                            }
                        }
                    }
                });
        startPaymentPanel.add(dateChooserFrom);
    }

    void setEndPaymentPanel(){
        dateChooserTo = new JDateChooser();
        dateChooserTo.setFont(customFont);
        dateChooserTo.setDate(localDate);
        endPaymentPanel.setVisible(false);
        endPaymentLabel.setVisible(false);
        endPaymentWarning.setVisible(false);
        dateChooserTo.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            dateTo = (Date) e.getNewValue();
                            switch (timeUnit) {
                                case "dni" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusDays(Integer.parseInt(timeUnitsTxt.getText()));
                                case "tygodnie" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusWeeks(Integer.parseInt(timeUnitsTxt.getText()));
                                case "miesiące" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusMonths(Integer.parseInt(timeUnitsTxt.getText()));
                            }

                            if(dateTo.before(java.util.Date.from(timeAmount.atZone(ZoneId.systemDefault())
                                    .toInstant()))){
                                dateToValid = false;
                                endPaymentWarning.setText("Zbyt krótki odstęp czasu!");
                                endPaymentWarning.setVisible(true);
                            }
                            else{
                                dateToValid = true;
                                endPaymentWarning.setVisible(false);
                            }
                        }
                    }
                });
        endPaymentPanel.add(dateChooserTo);
    }
    void setEndDateRadioButton() {
        endDateRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    isEndDateSelected = true;
                    endPaymentLabel.setVisible(true);
                    endPaymentPanel.setVisible(true);
                } else if (state == ItemEvent.DESELECTED) {
                    isEndDateSelected = false;
                    endPaymentLabel.setVisible(false);
                    endPaymentPanel.setVisible(false);
                }
            }
        });
    }
    void setCancelButton(){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(cancelPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }
    public JPanel getStandingOrderNextPanel(){
        return StandingOrderNextPanel;
    }
}
