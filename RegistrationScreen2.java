import javax.swing.*;    
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RegistrationScreen2{
    static JTextArea description;
    static JLabel firstNameLabel;
    static JTextField firstNameField;
    static JLabel lastNameLabel;
    static JTextField lastNameField;
    static JLabel cityLabel;
    static JTextField cityField;
    static JLabel addressLabel; 
    static JTextField addressField;
    static JLabel peselLabel; 
    static JTextField peselField;
    static JButton submitButton;
    static JButton exitButton;

    public static void CreateScreen(User user){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Registration form2");

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        firstNameLabel=new JLabel("First Name");
        gbc.insets = new Insets(10,5,5,10);
        frame.add(firstNameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridwidth=2;
        firstNameField=new JTextField();
        gbc.insets = new Insets(10,10,5,5);
        frame.add(firstNameField,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        lastNameLabel=new JLabel("Last Name");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(lastNameLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=2;
        lastNameField=new JTextField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(lastNameField,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=1;
        cityLabel=new JLabel("City");
        gbc.insets = new Insets(5,5,5,10);
        frame.add(cityLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=2;
        cityField = new JTextField();
        gbc.insets = new Insets(5,10,5,5);
        frame.add(cityField,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=1;
        addressLabel = new JLabel("Address");
        gbc.insets = new Insets(5,10,5,10);
        frame.add(addressLabel,gbc);

        gbc.gridx=1;
        gbc.gridy=3;
        gbc.gridwidth=2;
        addressField = new JTextField();
        gbc.insets = new Insets(5,10,5,10);
        frame.add(addressField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=1;
        peselLabel = new JLabel("PESEL");
        gbc.insets = new Insets(5,10,5,10);
        frame.add(peselLabel, gbc);

        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridwidth=2;
        peselField = new JTextField();
        gbc.insets = new Insets(5,10,5,10);
        frame.add(peselField, gbc);

        gbc.gridx=1;
        gbc.gridy=5;
        gbc.gridwidth=1;
        Connection con = DatabaseConnection.connectToDatabase("bank_system", "root", "password");
        try{
        Statement st = con.createStatement();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                user.firstName=firstNameField.getText();
                user.lastName=lastNameField.getText();
                user.city=cityField.getText();
                user.address=addressField.getText();
                user.pesel=peselField.getText();

                user.addUserData(st);
                frame.dispose();
                MainScreen.CreateScreen(user);
            }
        });
        }catch(SQLException e){
            System.out.println("Couldn't execute the query");
            System.out.println(e);
        }
        gbc.insets = new Insets(10,10,10,10);
        frame.add(submitButton,gbc);

        gbc.gridx=1;
        gbc.gridy=6;
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
        CreateScreen(null);
    }

}
