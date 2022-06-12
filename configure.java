import java.io.*;
import java.util.Scanner;

public class configure {
    public static void main(String[] args) throws FileNotFoundException,UnsupportedEncodingException {
        String user_email,user_password,db_name, db_username,db_password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter configuration data");
        System.out.print("email: ");
        user_email=sc.next();
        System.out.print("email password: ");
        user_password= sc.next();
        System.out.print("database name: ");
        db_name=sc.next();
        System.out.print("database username: ");
        db_username= sc.next();
        System.out.print("database password: ");
        db_password=sc.next();

        PrintWriter writer = new PrintWriter("user.conf", "UTF-8");
        writer.println(user_email);
        writer.println(user_password);
        writer.println(db_name);
        writer.println(db_username);
        writer.println(db_password);
        writer.close();
    }
}
