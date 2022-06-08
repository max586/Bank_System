package src;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Database {
    public static Statement st = connectToDatabase("bank_system","root","password");
    public static Statement connectToDatabase(String database_name,String username, String password){
        Connection con=null;
        Statement st=null;
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database_name,username,password);
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            System.out.println("Succesfully connected to "+ database_name+" database!"); 
        }catch(Exception e){ 
            System.out.println("Couldn't connect to "+database_name+" database!");
            System.out.println(e);}  
        return st;
    }
    public static void addUser(String username, String password, String email, String app_code){
        try {
            st.executeUpdate("insert into Users values('"+username+"','"+password+"','"+email+"','"+app_code+"');");
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void addUserData(String username, String firstName, String lastName, String sex,String phoneNumber, String city, String postcode, String street,String street_numb, String pesel){
        try {
            st.executeUpdate("insert into UsersData values('"+username+"','"+firstName+"','"+lastName+"','"+sex+"','"+phoneNumber+"','"+city+"','"+postcode+"','"+street+"','"+street_numb+"','"+pesel+"');");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }

    public static void addOrdinaryAccountNumber(String username, String ordinary_account_number){
        try {
            st.executeUpdate("insert into OrdinaryAccounts values('"+username+"','"+ordinary_account_number+"',0);");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void addSavingsAccountNumber(String username, String savings_account_number){
        try {
            st.executeUpdate("insert into SavingsAccounts values('"+username+"','"+savings_account_number+"',0,1);");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void addCard(String username, String card_number, String pin_code){
        try{
            st.executeUpdate("insert into Cards values ('"+username+"','"+card_number+"','"+pin_code+"');");
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void addToHistory(Statement st, String database, String operationDate, String transferType,
                                     String senderAccountNumber, String receiverAccountNumber,
                                     double transferAmount, String transferCurrency, double totalTransferCost,
                                     String transferTitle, String startDate, String endDate, int transferCycle,
                                     String transferCycleUnits){
        switch(database){
            case "HistoryOrdinary":
                try {
                    st.executeUpdate("insert into HistoryOrdinary values('"+operationDate+"','"+transferType+"','"+senderAccountNumber+"','"
                            +receiverAccountNumber+"','"+transferAmount+"','"+transferCurrency+"','"+totalTransferCost+"','"
                            +transferTitle+"','"+startDate+"','"+endDate+"','"+transferCycle+"','"+transferCycleUnits+"');");
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            case "HistorySavings":
                try {
                    st.executeUpdate("insert into HistorySavings values('"+operationDate+"','"+transferType+"','"+senderAccountNumber+"','"
                            +receiverAccountNumber+"','"+transferAmount+"','"+transferCurrency+"','"+totalTransferCost+"','"
                            +transferTitle+"','"+startDate+"','"+endDate+"','"+transferCycle+"','"+transferCycleUnits+"');");
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("Incorrect database!");
        }
    }
    public static String[][] getHistoryOrdinary(String username) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime day = LocalDateTime.now();
        day=day.minusMonths(1);
        String sday=day.toString().substring(0,10);
        try{
            int number_of_rows;
            ResultSet rs = st.executeQuery("select count(*) from HistoryOrdinary h join OrdinaryAccounts o on h.`Account nr from` = o.`Account number`  join UsersData u on o.username =u.username \n" +
                    "where h.`Operation Date` >= '"+sday+"'and o.username='"+username+"';");
            rs.next();
            number_of_rows=rs.getInt(1);
            rs = st.executeQuery("select h.*,u.* from HistoryOrdinary h join OrdinaryAccounts o on h.`Account nr from` = o.`Account number`  join UsersData u on o.username =u.username \n" +
                "where h.`Operation Date` >= '"+sday+"'and o.username='"+username+"';");
            String[][] transactions_history = new String[number_of_rows][22];
            rs.next();
            for(int i=0;i<number_of_rows;i++){
                for(int j=0;j<22;j++){
                    transactions_history[i][j]=rs.getString(j+1);
                }
            }
            return transactions_history;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static String[][] getHistorySavings(String username) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime day = LocalDateTime.now();
        day=day.minusMonths(1);
        String sday=day.toString().substring(0,10);
        try{
            int number_of_rows;
            ResultSet rs = st.executeQuery("select count(*) from HistorySavings h join SavingsAccounts o on h.`Account nr from` = o.`Account number`  join UsersData u on o.username =u.username \n" +
                    "where h.`Operation Date` >= '"+sday+"'and o.username='"+username+"';");
            rs.next();
            number_of_rows=rs.getInt(1);
            rs = st.executeQuery("select h.*,u.* from HistorySavings h join SavingsAccounts o on h.`Account nr from` = o.`Account number`  join UsersData u on o.username =u.username \n" +
                    "where h.`Operation Date` >= '"+sday+"'and o.username='"+username+"';");
            String[][] transactions_history = new String[number_of_rows][22];
            rs.next();
            for(int i=0;i<number_of_rows;i++){
                for(int j=0;j<22;j++){
                    transactions_history[i][j]=rs.getString(j+1);
                }
            }
            return transactions_history;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void addCredit(String username, float amount, float amount_payed, String start_date, int duration){
        try{
            st.executeUpdate("insert into Credits values('"+username+"','"+amount+"','"+amount_payed+"','"+start_date+"','"+duration+"');");
        }catch(SQLException e) {
        System.out.println("Couldn't execute the query");
        System.out.println(e);
    }
    }
    public static String[] getCredit(String username){
        String[] Credit = new String[4];
        try{
            ResultSet rs = st.executeQuery("select * from Credits where username='"+username+"';");
            rs.next();
            for(int i=2;i<=5;i++){Credit[i-2]=rs.getString(i);}
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return Credit;
    }
    public static String getEmail(String username){
        try {
            ResultSet rs = st.executeQuery("select email from Users where username='"+username+"';");
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return null;
    }
    public static String getAppCode(String username){
        try {
            ResultSet rs = st.executeQuery("select appCode from Users where username='"+username+"';");
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return null;
    }
    public static String getOrdinaryAccountNumber(String username){
        try{
            ResultSet rs = st.executeQuery("select nr from OrdinaryAccounts where username='"+username+"';");
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return null;
    }
    public static String getSavingsAccountNumber(String username){
        try{
            ResultSet rs = st.executeQuery("select nr from SavingsAccounts where username='"+username+"';");
            rs.next();
            return rs.getString(1);
        }
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return null;
    }
    public static float getOrdinaryAccountBalance(String username){
        try{
            ResultSet rs = st.executeQuery("select balance from OrdinaryAccounts where username='"+username+"';");
            rs.next();
            return rs.getFloat(1);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return -1;
    }
    public static float getSavingsAccountBalance(String username){
        try{
            ResultSet rs = st.executeQuery("select balance from SavingsAccounts where username='"+username+"';");
            rs.next();
            return rs.getFloat(1);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return -1;
    }
    public static String getUsernameByCard(String nr){
        try {
            ResultSet rs = st.executeQuery("select username from Cards where nr='" + nr + "';");
            rs.next();
            return rs.getString(1);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return null;
    }
    public static String[] getCard(String username){
        String[] card = new String[2];
        try {
            ResultSet rs = st.executeQuery("select nr,pin from Cards where username='"+username+"';");
            rs.next();
            card[0] = rs.getString(1);
            card[1] = rs.getString(2);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return card;
    }
    public static String[] getUserData(String username){
        String[] user_data = new String[9];
        try{
            ResultSet rs = st.executeQuery("select * from UsersData where username='"+username+"';");
            rs.next();
            user_data[0]=rs.getString(2);
            user_data[1]=rs.getString(3);
            user_data[2]=rs.getString(4);
            user_data[3]=rs.getString(5);
            user_data[4]=rs.getString(6);
            user_data[5]=rs.getString(7);
            user_data[6]=rs.getString(8);
            user_data[7]=rs.getString(9);
            user_data[8]=rs.getString(10);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return user_data;
    }
    public static void setUsername(String username, String new_username){
        try {
            st.executeUpdate("update Users set username='"+new_username+"'where username='"+username+"';");
            username=new_username;
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void setPassword(String username, String new_password){
        try {
            st.executeUpdate("update Users set password='"+new_password+"'where password='"+username+"';");
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void setEmail(String username, String new_email) {
        try {
            st.executeQuery("update Users set username='" + new_email + "'where username='" + username + "';");
        } catch (SQLException e) {
            //TODO: handle exception
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void setOrdinaryAccountBalance(String username, float new_balance){
        try{
            st.executeUpdate("update OrdinaryAccounts set balance='"+new_balance+"' where username='"+username+"';");
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void setSavingsAccountBalance(String username, float new_balance){
        try{
            st.executeUpdate("update SavingsAccounts set balance='"+new_balance+"' where username='"+username+"';");
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void setCreditAmountPayed(String username,float amount_payed){
        try{
            st.executeUpdate("update Credits set `amount payed`='"+amount_payed+"'where username='"+username+"';");
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static boolean isUsernameTaken(String username){
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
    public static boolean verifyUser(String username, String password){
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

    public static boolean hasCredit(String username){
        try{
            ResultSet rs = st.executeQuery("select count(*) from Credits where username='"+username+"';");
            rs.next();
            return rs.getInt(1)==1;
        }
        catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return false;
    }
    public static boolean hasCard(String username){
        try {
            ResultSet rs = st.executeQuery("select count(*) from Cards where username='" + username + "';");
            rs.next();
            return rs.getInt(1)==1;
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return false;
    }
    public static boolean verifyCard(String nr, String pin){
        try {
            ResultSet rs = st.executeQuery("select count(*) from Cards where nr='" + nr + "' and pin='"+pin+"';");
            rs.next();
            return rs.getInt(1)==1;
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return false;
    }
    public static boolean verifyOrdinaryAccountNumber(String ordinaryAccountNumber){
        try{
            ResultSet rs = st.executeQuery("select count(*) from OrdinaryAccounts where `Account number`='"+ordinaryAccountNumber+"';");
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

    public static boolean verifySavingsAccountNumber(String savingsAccountNumber){
        try{
            ResultSet rs = st.executeQuery("select count(*) from SavingsAccounts where `Account number`='"+savingsAccountNumber+"';");
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

    public static boolean verifyPhoneNumber(String phoneNumber){
        try{
            ResultSet rs = st.executeQuery("select count(*) from UsersData where `Phone number`='"+phoneNumber+"';");
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

    public static String getUserByPhone(String phoneNumber){
        String userName = "";
        try{
            ResultSet rs = st.executeQuery("select * from UsersData where `Phone number`='"+phoneNumber+"';");
            rs.next();
            userName=rs.getString(1);
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        return userName;
    }
    public static void deleteUser(String username){
       try{
           st.executeUpdate("delete from Users where username='"+username+"';");
       }catch(SQLException e) {
           System.out.println("Couldn't execute the query");
           System.out.println(e);
       }
    }
    public static void deleteCredit(String username){
        try{
            st.executeUpdate("delete from Credits where username='"+username+"';");
        }catch(SQLException e) {
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
    //Statement st = connectToDatabase("bank_system", "root","password");
    //String[] card = getCard("test_user");
    //for(int i=0;i<2;i++){System.out.println(card[i]);}
        try {
            String[][] rez = getHistoryOrdinary("test_user");
            for(int i=0;i<3;i++){
                for(int j=0;j<22;j++){
                    System.out.print(rez[i][j]+"\t");
                }
                System.out.println();
            }
        }catch(Exception e){}
    }
}
