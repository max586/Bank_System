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
    public MainFrame frame;
    public JPanel cancelPanel;
    public JLabel panelTitleLabel;
    public JComboBox timeUnitsComboBox;
    public JTextField timeUnitsTxt;
    public JPanel StandingOrderNextPanel;
    public JLabel startPaymentLabel;
    public JPanel startPaymentPanel;
    public JPanel endPaymentPanel;
    public JButton cancelButton;
    public JButton nextButton;
    public JLabel startPaymentWarning;
    public JRadioButton endDateRadioButton;
    public JLabel endPaymentLabel;
    public JLabel endPaymentWarning;
    public JLabel timeUnitsWarning;
    public JLabel timeUnitsWarning2;
    public JPanel timerPanel;
    public JLabel timeLabel;
    public JDateChooser dateChooserFrom;
    public JDateChooser dateChooserTo;
    public boolean isEndDateSelected;
    public Date dateFrom;
    public Date localDate;
    public Date dateTo;
    public LocalDateTime timeAmount;
    public boolean dateFromValid;
    public Font customFont;
    public KeyAdapter numbersOnly;
    public Vector<Boolean> validation;
    public boolean timeUnitsValid;
    public String timeUnit;
    public boolean dateToValid;
    public Map<String,String> transferData;
    public User user;
    public User receiver;
    public AccountChoosed accountChoosedUser;
    public AccountChoosed accountChoosedReceiver;
    public String userAccountNumber;
    public double userAccountBalance;

    public StandingOrderNextStep(AccountChoosed accountChoosed1, User user1, AccountChoosed accountChoosed2, User receiver1,Map<String,String> transferData1, MainFrame mainFrame, JPanel standingOrderPanel) throws IOException, FontFormatException {
        user = user1;
        receiver = receiver1;
        accountChoosedUser = accountChoosed1;
        accountChoosedReceiver = accountChoosed2;
        if(accountChoosedUser ==AccountChoosed.ORDINARYACCOUNT) {
            userAccountNumber = user.ordinary_account_number;
            userAccountBalance = Math.round(user.ordinary_account_balance*100.0)/100.0;
        }else{
            userAccountNumber = user.savings_account_number;
            userAccountBalance = Math.round(user.savings_account_balance*100.0)/100.0;
        }
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        StandingOrderNextPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        transferData = transferData1;
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
                        case "day" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusDays(Integer.parseInt(timeUnitsTxt.getText()));
                        case "week" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusWeeks(Integer.parseInt(timeUnitsTxt.getText()));
                        case "month" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusMonths(Integer.parseInt(timeUnitsTxt.getText()));
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
                            endPaymentWarning.setText("To short time interval");
                            endPaymentWarning.setVisible(true);
                            validation.add(false);
                        } else {
                            endPaymentWarning.setVisible(false);
                            validation.add(true);
                        }
                    }
                    if(!validation.contains(false)){
                        transferData.replace("type","Standing order "+"every "+timeUnitsTxt.getText()+" "+timeUnit);
                        transferData.put("cicles",timeUnitsTxt.getText());
                        transferData.put("timeunit",timeUnit);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        transferData.put("startdate",formatter.format(dateChooserFrom.getDate()));
                        if(isEndDateSelected) transferData.put("enddate",formatter.format(dateChooserTo.getDate()));
                        TransferNextStep transferNextStep = new TransferNextStep(accountChoosedUser, user,accountChoosedReceiver,receiver, transferData, frame, StandingOrderNextPanel);
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
            if (c.getSelectedItem() == "day") {
                timeUnit = "day";
                timeUnitsValid = true;
                timeUnitsWarning2.setVisible(false);
            }
            else if(c.getSelectedItem()=="week"){
                timeUnit = "week";
                timeUnitsValid=true;
                timeUnitsWarning2.setVisible(false);
            }
            else if(c.getSelectedItem()=="month"){
                timeUnit = "month";
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
                                startPaymentWarning.setText("Date cannot be earlier than today");
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
                                case "day" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusDays(Integer.parseInt(timeUnitsTxt.getText()));
                                case "week" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusWeeks(Integer.parseInt(timeUnitsTxt.getText()));
                                case "month" -> timeAmount = LocalDateTime.from(dateFrom.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().getId()))).plusMonths(Integer.parseInt(timeUnitsTxt.getText()));
                            }

                            if(dateTo.before(java.util.Date.from(timeAmount.atZone(ZoneId.systemDefault())
                                    .toInstant()))){
                                dateToValid = false;
                                endPaymentWarning.setText("To short time interval");
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
