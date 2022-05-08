import java.util.regex.*;

public class DataValidation {
    static boolean passwordMatches(String password1,String password2){return password1.equals(password2);}
    static boolean passwordLength(String password){return password.length()>=12;}
    static boolean letterInPassword(String password){
        return Pattern.matches(".*[a-z].*",password)&&Pattern.matches(".*[A-Z].*", password);
    }
    static boolean digitInPassword(String password){
        return Pattern.matches(".*[0-9].*",password);
    }
    static boolean specialCharInPassword(String password){
        return Pattern.matches(".*\\W.*",password);
    }

    //PESEL check
    static boolean peselIsValid(String pesel, boolean man){
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

    static boolean nameIsValid(String name){
        if(name.length()>=3 && name.length()<=10){
            String pattern1="[A-Z][a-z]*", pattern2=".*[aAeEiIoOuUyY].*";
            if(Pattern.matches(pattern1, name) && Pattern.matches(pattern2, name)){return true;}
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(nameIsValid("Max"));
        //System.out.println(Pattern.matches("(0[0-9]|[10,11])","11"));
        //System.out.println(peselIsValid("84122816732", true));
    }
}
