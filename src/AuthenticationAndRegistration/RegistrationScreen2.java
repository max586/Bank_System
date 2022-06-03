package src.AuthenticationAndRegistration;

import src.DataValidation;
import src.Database;
import src.Database.*;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class RegistrationScreen2 extends Screen{
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField cityField;
    private JTextField addressField;
    private JComboBox sexComboBox;
    private JTextField peselField;
    private JButton submitButton;
    private JButton returnButton;
    private JButton exitButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel cityLabel;
    private JLabel addressLabel;
    private JLabel peselLabel;
    private JLabel sexLabel;
    private JPanel panel;

    public RegistrationScreen2(){}
    public RegistrationScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        
        frame.setContentPane(panel);

        Statement st = Database.connectToDatabase("bank_system", "root", "password");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                String firstName=firstNameField.getText();
                String lastName=lastNameField.getText();
                String sex=(String)sexComboBox.getSelectedItem();
                String pesel=peselField.getText();
                String city = cityField.getText();
                String address = addressField.getText();
                boolean lname_is_valid= DataValidation.nameIsValid(lastName), fname_is_valid=DataValidation.nameIsValid(firstName),
                        pesel_is_valid=DataValidation.peselIsValid(pesel, (sex=="M"));
                if(!fname_is_valid){firstNameField.setText("First name is invalid");}
                if(!lname_is_valid){lastNameField.setText("Last name is invalid");}
                if(!pesel_is_valid){peselField.setText("PESEL is invalid");}
                if(fname_is_valid&&lname_is_valid&&pesel_is_valid){

                    Database.addUser(st, user.username, user.password, user.email);
                    Database.addUserData(st, user.username, user.firstName, user.lastName, user.sex, user.city, user.address, user.pesel);

                    user.firstName=firstName;
                    user.lastName=lastName;
                    user.sex=sex;
                    user.city=city;
                    user.address=address;
                    user.pesel=pesel;

                    frame.dispose();
                    if(next_screen!=null){
                        new CreatedAccountAndCardNumberScreen(user, RegistrationScreen2.this, new Screen()).CreateScreen();
                    }
                }
            }
        });

        returnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                if(prev_screen!=null){
                    prev_screen.CreateScreen();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        User user = new User();
        user.username = "new_user";
        user.password = "password";
        user.email = "maks.ovsienko2@gmail.com";
        new RegistrationScreen2(user,null,new Screen()).CreateScreen();
    }

}
