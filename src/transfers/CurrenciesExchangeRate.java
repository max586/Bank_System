package src.transfers;

import javax.money.NumberValue;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CurrenciesExchangeRate {
    public final Map<String,String> currencies;
    public final Map<Map.Entry<String,String>,Double> currenciesWithAmounts;
    String baseCurrencyCode = "PLN";
    CurrenciesExchangeRate() {
        currencies = new LinkedHashMap<>();
        currenciesWithAmounts = new LinkedHashMap<>();
        try {
            setCurrencies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setCurrenciesWithAmounts();
    }
    void setCurrencies() throws IOException {
        ZipFile zip = new ZipFile("currenciesNBP.zip");
        ZipEntry ze = zip.getEntry("currenciesNBP.csv");
        InputStream inputStream = zip.getInputStream(ze);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = br.readLine();
        line = br.readLine();
        while(line!=null){
            CurrencyEntry currencyEntry = new CurrencyEntry(line);
            currencies.put(currencyEntry.getCurrencyCode(),currencyEntry.getCurrencyName());
            line = br.readLine();
        }
        currencies.remove("UAH");
        currencies.remove("CLP");
        currencies.remove("XDR");
    }
//    void setCurrenciesWithAmounts(){
//        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
//        for (Map.Entry<String,String> entry : currencies.entrySet()){
//            ExchangeRate rate = exchangeRateProvider.getExchangeRate(entry.getValue(), baseCurrencyCode);
//            NumberValue factor = rate.getFactor();
//            double currencyAmount = factor.doubleValue();
//            currenciesWithAmounts.put(entry,currencyAmount);
//        }
//    }
    Map<String,String> getCurrencies(){
        return currencies;
    }
    double getCurrencyAmount(String currencyCode){
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate(currencyCode, baseCurrencyCode);
        NumberValue factor = rate.getFactor();
        double currencyAmount = factor.doubleValue();
        return currencyAmount;
    }
}

class CurrencyEntry {
    String[] elems;
    CurrencyEntry(String x){
        elems = x.split(",");
    }
    String getCurrencyName(){
        return elems[0];
    }
    String getCurrencyCode(){
        return elems[1];
    }
}