import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class User {
    public String username;
    public String password;
    public String email;   
    public String firstName;
    public String lastName;
    public String city;
    public String address;
    public String pesel; 
    public void addUser(Statement st){
            try {
                st.executeUpdate("insert into Users values('"+username+"','"+password+"','"+email+"');");            
            } catch (SQLException e) {
                //TODO: handle exception
                System.out.println("Couldn't execute the query");
                System.out.println(e);
            }
    }
    public void setEmail(Statement st){
        try {
            ResultSet rs = st.executeQuery("select email from Users where username='"+username+"';");
            rs.next();
            email=rs.getString(1);
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public void addUserData(Statement st){
        try {
            st.executeUpdate("insert into UsersData values('"+username+"','"+firstName+"','"+lastName+"','"+city+"','"+address+"','"+pesel+"');");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public void changeUsername(Statement st, String new_username){
        try {
            st.executeUpdate("update Users set username='"+new_username+"'where username='"+username+"'';");
            username=new_username;
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public void changePassword(Statement st, String new_password){
        try {
            st.executeQuery("update Users set password='"+new_password+"'where password='"+password+"';");
            password = new_password;
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public void changeEmail(Statement st,String new_email){
        try {
            st.executeQuery("update Users set username='"+new_email+"'where username='"+email+"'';");
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public void updateUserData(){}
    public boolean isUsernameTaken(Statement st){
        try{
            ResultSet rs = st.executeQuery("select count(*) from Users where username='"+username+"';");
            rs.next();
            return(rs.getInt(1) == 1);
        }
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return false;
    }
    public boolean verifyUser(Statement st){
        try{
            ResultSet rs = st.executeQuery("select count(*) from Users where username='"+username+"'and password='"+password+"';");
            rs.next();
            int res=rs.getInt(1);
            return(res == 1);
        }
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return false;
    }
    public void displayUser(Statement st){
            System.out.println("User data:");
            System.out.println("username - "+username);
            System.out.println("password - "+password);    
            System.out.println("email - "+email);
    }
}
