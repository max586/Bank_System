package transfers;

import mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

public class ForeignStandardTransfer extends StandardTransfer implements Transfer{
    private final Map<String,String> currencies;
    private final CurrenciesExchangeRate currenciesExchangeRate;
    private String currency;
    private double converter;
    private java.util.List<String> countriesISO;
    ForeignStandardTransfer(MainFrame mainFrame, Map<String, String> senderData1) throws IOException, FontFormatException {
        super(mainFrame,senderData1);
        isCountry = true;
        panelTitleLabel.setText("Przelew zagraniczny");
        accountNumberCountryLabel.setVisible(false);
        converter = 1.0;
        transferAmountCurrencyLabel.setVisible(false);
        currenciesExchangeRate = new CurrenciesExchangeRate();
        currencies = currenciesExchangeRate.getCurrencies();
        countriesISO = new ArrayList<String>(Arrays.asList(Locale.getISOCountries()));
        countriesISO.remove("PL");
        setAccountNumberCountryComboBox();
        setcurrencyCombo();
        setTransferAmountTxt(transferAmount1Txt);
        setTransferAmountTxt(transferAmount2Txt);
    }
    void setTransferData(){
        transferData.put("waluta",currency);
    }

    void setAccountNumberCountryComboBox(){
        accountNumberCountryComboBox.setVisible(true);
        accountNumberCountryComboBox.addItem("PL");
        for(String ISOcode: countriesISO){
            accountNumberCountryComboBox.addItem(ISOcode);
        }
        accountNumberCountryComboBox.addActionListener(event -> {
            JComboBox c = (JComboBox) event.getSource();
            countryISO = (String) c.getSelectedItem()+" ";
        });
    }
    void setcurrencyCombo(){
        currencyComboBox.setVisible(true);
        currencyComboBox.addItem("PLN");
        for(Map.Entry<String,String> entry:currencies.entrySet()){
            currencyComboBox.addItem(entry.getKey());
        }
        currencyComboBox.addActionListener(event -> {
            JComboBox c = (JComboBox) event.getSource();
            String currencyCode = (String) c.getSelectedItem();
            currency = currencyCode;
            setTransferData();
            converter = currenciesExchangeRate.getCurrencyAmount(currencyCode);
            currencyInfoLabel.setText(" " + currencies.get(currencyCode) + " [1PLN = " + String.format("%.4f", converter) + "]");
            currencyInfoLabel.setVisible(true);
            finalTransferAmount = (transferAmount1 + transferAmount2) * converter;
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
                        finalTransferAmount = converter*finalTransferAmount;
                        isAmountValid = !(finalTransferAmount > senderAmount);
                    }
                    else{
                        k1 = Double.parseDouble("0." + s1);
                        transferAmount2=k1;
                        finalTransferAmount = transferAmount1+transferAmount2;
                        finalTransferAmount = converter*finalTransferAmount;
                        isAmountValid = !(finalTransferAmount>senderAmount);
                    }
                    transferAmountWarning.setText("Nie masz wystarczających środków");
                    transferAmountWarning.setVisible(!isAmountValid);
                }
            }
        });
    }
}
