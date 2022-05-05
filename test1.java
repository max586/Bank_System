import java.util.regex.*;

public class test1 {
    static boolean isPasswordValid(String password){
        String p1=".*[a-z].*", p2=".*[A-Z].*", p3=".*[0-9].*", p4=".*\\W.*";
        return (password.length()>=12) && (Pattern.matches(p1, password)) && (Pattern.matches(p2, password)) && (Pattern.matches(p3, password)) && (Pattern.matches(p4, password));
    }
    
    public static void main(String[] args) {
        System.out.println(isPasswordValid("polaK342ppozoz."));
        
        //System.out.println(Pattern.matches(".*\\p{Lower}.*","AAAAa"));
        //System.out.println(Pattern.matches(".*\\p{Lower}.*","AAAAa"));
    }
    
}
