
import javax.swing.*;    
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RegistrationScreen1{
    static JTextArea description;
    static JLabel usernameLabel;
    static JTextField usernameField;
    static JLabel loginLabel;
    static JTextField loginField;
    static JLabel passwordLabel;
    static JPasswordField passwordField;
    static JLabel repeatPasswordLabel; 
    static JPasswordField repeatPasswordField;
    static JLabel passwordMatchesField;
    static JLabel passwordLengthField;
    static JLabel passwordLetterField;
    static JLabel passwordDigitField;
    static JLabel passwordSpecialCharField;
    static JLabel emailLabel;
    static JTextField emailField;
    static JButton submitButton;
    static JButton exitButton;

    public static void CreateScreen(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Registration form1");

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        usernameLabel=new JLabel("username");
        gbc.insets = new Insets(10,5,5,10);
        frame.add(usernameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        usernameField=new JTextField();
        gbc.insets = new Insets(10,10,5,5);
        frame.add(usernameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        passwordLabel=new JLabel("password");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(passwordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        passwordField=new JPasswordField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(passwordField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        repeatPasswordLabel=new JLabel("enter again your password");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(repeatPasswordLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=2;
        repeatPasswordField = new JPasswordField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(repeatPasswordField,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=3;
        passwordMatchesField = new JLabel();
        passwordMatchesField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordMatchesField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=3;
        passwordLengthField = new JLabel();
        passwordLengthField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLengthField,gbc);

        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridwidth=3;
        passwordLetterField = new JLabel();
        passwordLetterField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordLetterField,gbc);

        gbc.gridx=0;
        gbc.gridy=6;
        gbc.gridwidth=3;
        passwordDigitField = new JLabel();
        passwordDigitField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordDigitField,gbc);

        gbc.gridx=0;
        gbc.gridy=7;
        gbc.gridwidth=3;
        passwordSpecialCharField = new JLabel();
        passwordSpecialCharField.setVisible(false);
        gbc.insets = new Insets(5,10,5,10);
        frame.add(passwordSpecialCharField,gbc);

        gbc.gridx=0;
        gbc.gridy=8;
        gbc.gridwidth=1;
        emailLabel=new JLabel("email");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(emailLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridwidth=2;
        emailField=new JTextField();
        gbc.insets = new Insets(5,10,10,5);
        frame.add(emailField,gbc);

        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridwidth=1;
        Connection con = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
        try{
        Statement st = con.createStatement();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Boolean password_is_valid=true,username_is_taken;
                User new_user = new User();
                new_user.username = usernameField.getText();
                if(new_user.isUsernameTaken(st)){
                    username_is_taken=true;
                    usernameField.setText("username is already taken");
                }
                else{usernameField.setText("username is saved");username_is_taken=false;}
                new_user.password = new String(passwordField.getPassword());
                String repeated_password = new String(repeatPasswordField.getPassword());
                new_user.email = emailField.getText();

                passwordMatchesField.setVisible(true);
                passwordLengthField.setVisible(true);
                passwordLetterField.setVisible(true);
                passwordSpecialCharField.setVisible(true);
                passwordDigitField.setVisible(true);

                if(DataValidation.passwordMatches(new_user.password, repeated_password)){passwordMatchesField.setText("ok");}
                else{passwordMatchesField.setText("passwords dont match");password_is_valid=false;}
                if(DataValidation.passwordLength(new_user.password)){passwordLengthField.setText("ok");}
                else{passwordLengthField.setText("password too short");password_is_valid=false;}
                if(DataValidation.letterInPassword(new_user.password)){passwordLetterField.setText("ok");}
                else{passwordLetterField.setText("at least one upper or lowercase letter");password_is_valid=false;}
                if(DataValidation.digitInPassword(new_user.password)){passwordDigitField.setText("ok");}
                else{passwordDigitField.setText("at least one digit");password_is_valid=false;}
                if(DataValidation.specialCharInPassword(new_user.password)){passwordSpecialCharField.setText("ok");}
                else{passwordSpecialCharField.setText("at least one special character");password_is_valid=false;}

                if(password_is_valid && !username_is_taken){
                    new_user.addUser(st);
                    frame.dispose();
                    EmailVerificationScreen.main(new_user);
                }
            }
        });
        }catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        gbc.insets = new Insets(10,10,10,10);
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=10;
        gbc.gridwidth=1;
        exitButton = new JButton("Exit");
        gbc.insets = new Insets(10,10,10,10);
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
