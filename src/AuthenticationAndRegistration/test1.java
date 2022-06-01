package src.AuthenticationAndRegistration;

import java.util.regex.*;
import java.time.LocalDateTime;
import java.util.Random;

public class test1 {
    static boolean isPasswordValid(String password){
        String p1=".*[a-z].*", p2=".*[A-Z].*", p3=".*[0-9].*", p4=".*\\W.*";
        return (password.length()>=12) && (Pattern.matches(p1, password)) && (Pattern.matches(p2, password)) && (Pattern.matches(p3, password)) && (Pattern.matches(p4, password));
    }

    static String generateAccountNumber(){
        String account_number="1100";
        String date = LocalDateTime.now().toString().substring(0,10);
        date=date.replace("-", "");
        account_number+=date;
        Random rnd = new Random();
        for(int i=0;i<12;i++){
            account_number+=Integer.toString(rnd.nextInt(10));
        }

        //checksum
        int[] account_number_arr = new int[24];
        for(int i=0;i<24;i++){account_number_arr[i]=Integer.parseInt(String.valueOf(account_number.charAt(i)));}

        int checksum=0, coef=1;
        for(int i=0;i<24;i++){
            checksum+=account_number_arr[i]*coef;
            if(coef==1){coef=3;}
            if(coef==3){coef=7;}
            if(coef==7){coef=1;}
        }
        checksum=checksum%100;
        account_number=Integer.toString((checksum-checksum%10)/10)+Integer.toString(checksum%10)+account_number;
        return account_number;
    }

    static String generateCardNumber(String account_number){
        //MasterCard IIN range 51-55#### or 222100‑272099
        String card_number="";
        Random rnd = new Random();
        if(rnd.nextInt(2)==1){//starts with 51-55
            card_number+=Integer.toString(rnd.nextInt(55-51+1)+51);
            for(int i=0;i<4;i++){card_number+=Integer.toString(rnd.nextInt(10));}
        }   
        else{//starts with 222100-272099
            card_number+=String.valueOf(rnd.nextInt(272099-222100+1)+222100);
        }

        card_number+=account_number.substring(0,2)+account_number.substring(6, 14);
        return card_number;
    }

    static String generateCVVNumber(){
        Random rnd = new Random();
        return Integer.toString(rnd.nextInt(1000-100)+100);
    }
    static String generatePINCode(){
        Random rnd = new Random();
        return Integer.toString(rnd.nextInt(10000-1000)+100);
    }
    
    public static void main(String[] args) {
        //System.out.println(isPasswordValid("polaK342ppozoz."));
        String an=generateAccountNumber();
        String cn=generateCardNumber(an);
        System.out.println(an);
        System.out.println(cn);
        //System.out.println(Pattern.matches(".*\\p{Lower}.*","bank_systemAa"));
        //System.out.println(Pattern.matches(".*\\p{Lower}.*","bank_systemAa"));
    }
    
}
