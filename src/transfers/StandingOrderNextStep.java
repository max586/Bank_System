package transfers;

import com.toedter.calendar.JDateChooser;
import mainFrame.MainFrame;

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
    public Map<String,String> senderData;
    public Map<String,String> receiverData;
    public Map<String,String> transferData;
    public double senderAmount;

    public StandingOrderNextStep(MainFrame mainFrame, JPanel standingOrderPanel, Map<String,String> senderData1, Map<String,String> receiverData1, Map<String,String> transferData1) throws IOException, FontFormatException {
        senderData = senderData1;
        receiverData = receiverData1;
        transferData = transferData1;
        senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
        frame = mainFrame;
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
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        transferData.put("startdata",formatter.format(dateChooserFrom.getDate()));
                        if(isEndDateSelected) transferData.put("enddata",formatter.format(dateChooserTo.getDate()));
                        TransferNextStep transferNextStep = new TransferNextStep(frame, StandingOrderNextPanel,senderData,receiverData,transferData);
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
