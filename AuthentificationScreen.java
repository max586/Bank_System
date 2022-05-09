import javax.swing.*;
import java.sql.Statement;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AuthentificationScreen{
    static JButton exitButton;
    static JLabel usernameLabel;
    static JTextField usernameField;
    static JLabel passwordLabel;
    static JPasswordField passwordField;
    static JButton signInButton;
    static JButton signUpButton;

    public static void CreateScreen(){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Authentification screen");

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(0,5,5,10);
        usernameLabel = new JLabel("login");
        frame.add(usernameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(0,10,5,5);
        usernameField = new JTextField();
        frame.add(usernameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(5,5,10,10);
        passwordLabel = new JLabel("password");
        frame.add(passwordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(5,10,10,5);
        passwordField = new JPasswordField();
        frame.add(passwordField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth=1;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(10,5,0,5);
        signInButton = new JButton("Sign in");
        signInButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                User user = new User();
                user.username=usernameField.getText();
                user.password=new String(passwordField.getPassword());
                Connection con = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
                try{
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    if(user.verifyUser(st)){
                        JOptionPane.showMessageDialog(frame, "user successfully verified");
                        frame.dispose();
                        user.getUserAccounts(st);
                        if(user.ordinary_account_number==null || user.savings_account_number==null){
                            MainScreen.CreateScreen(user);
                        }
                        else{
                            ChooseAccountNumberScreen.CreateScreen(user);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "user doesn't exist");
                    }
                }catch(SQLException e2){}
            }
        });
        frame.add(signInButton,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth=1;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(0,5,0,5);
        signUpButton = new JButton("sign up");
        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                frame.dispose();
                RegistrationScreen1.CreateScreen();
            }
        });
        frame.add(signUpButton,gbc);
                
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth=1;
        gbc.weightx=0.5;
        gbc.weighty=0.5;
        gbc.insets = new Insets(0,5,5,5);
        exitButton = new JButton("exit");
        exitButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                        frame.dispose();  
                    }  
        }); 
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {  
        CreateScreen();
    }  
}  

