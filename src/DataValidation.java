package src;

import java.util.regex.*;

public class DataValidation {
    public static boolean isUsernameValid(String username){return Pattern.matches("\\w+",username) && username.length()>=3 && username.length()<=30;}
    public static boolean passwordMatches(String password1,String password2){return password1.equals(password2);}
    public static boolean passwordLength(String password){return password.length()>=12 && password.length()<=30;}
    public static boolean letterInPassword(String password){
        return Pattern.matches(".*[A-Za-z].*",password);
    }
    public static boolean digitInPassword(String password){
        return Pattern.matches(".*[0-9].*",password);
    }
    public static boolean specialCharInPassword(String password){
        return Pattern.matches(".*\\W.*",password);
    }
    public static boolean passwordIsValid(String password1,String password2){
        return passwordMatches(password1,password2)&&passwordLength(password1)&&digitInPassword(password1)&&letterInPassword(password1)
                &&specialCharInPassword(password1);
    }
    public static boolean peselIsValid(String pesel, boolean man){
        //numer pesel
        String pattern="\\d{2}(0[0-9]|10|11|12)(0[0-9]|1[0-9]|2[0-9]|30|31)\\d{5}";

        if(Pattern.matches(pattern, pesel)){
            int[] pesel_arr = new int[11];
            try{
            for(int i=0;i<11;i++){pesel_arr[i]=Integer.parseInt(String.valueOf(pesel.charAt(i)));}
            }catch(NumberFormatException e){
                System.out.println(e);
            }
            if((man && pesel.charAt(9)%2==1)||(!man && pesel.charAt(9)%2==0)){
                int control_sum=10-(pesel_arr[0]%10+(pesel_arr[1]*3)%10+(pesel_arr[2]*7)%10+(pesel_arr[3]*9)%10+
                pesel_arr[4]%10+(pesel_arr[5]*3)%10+(pesel_arr[6]*7)%10+(pesel_arr[7]*9)%10+pesel_arr[8]%10+(pesel_arr[9]*3)%10)%10;
                if(pesel_arr[10]==control_sum){
                    return true;
                }
                else{return false;}    
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static boolean nameIsValid(String name){
        if(name.length()>=3 && name.length()<=10){
            String pattern1="[A-Z][a-z]*", pattern2=".*[aAeEiIoOuUyY].*";
            if(Pattern.matches(pattern1, name) && Pattern.matches(pattern2, name)){return true;}
            return false;
        }
        return false;
    }
    public static boolean cityIsValid(String address){
        if(address.length()>=3 && address.length()<=20){
            String pattern="[A-Z][a-z]*";
            return Pattern.matches(pattern,address);
        }
        return false;
    }
    public static boolean postcodeIsValid(String postcode){
        return Pattern.matches("\\d\\d-\\d\\d\\d",postcode);
    }
    public static boolean phoneNrIsValid(String phone_nr){
        return Pattern.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d",phone_nr);
    }
    public static boolean streetNrIsValid(String street_nr){
        return street_nr.length()<10 && Pattern.matches("\\d+(/\\d+)?",street_nr);
    }
    public static boolean isNumber(String word){
        return word.length()<=10 && Pattern.matches("[1-9][0-9]*\\.?[0-9]*",word);
    }
    public static void main(String[] args) {
        System.out.println(nameIsValid("Max"));
        //System.out.println(addressIsValid("ul. Ankm, 10"));
        System.out.println(cityIsValid("Abc"));

        //System.out.println(Pattern.matches("(0[0-9]|[10,11])","11"));
        //System.out.println(peselIsValid("84122816732", true));
    }
}
