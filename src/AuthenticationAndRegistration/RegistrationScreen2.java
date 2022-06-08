package src.AuthenticationAndRegistration;

import src.DataValidation;
import src.Database;
import src.Database.*;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Statement;

public class RegistrationScreen2 extends Screen{
    public JTextField lastNameField;
    public JTextField firstNameField;
    public JTextField cityField;
    public JTextField addressField;
    public JComboBox sexComboBox;
    public JTextField peselField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel firstNameLabel;
    public JLabel lastNameLabel;
    public JLabel cityLabel;
    public JLabel addressLabel;
    public JLabel peselLabel;
    public JLabel sexLabel;
    public JPanel panel;
    public JLabel timerLabel;
    public int counter=0;

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
                //String phone_nr=();
                String pesel=peselField.getText();
                String city = cityField.getText();
                String address = addressField.getText();
                boolean lname_is_valid= DataValidation.nameIsValid(lastName), fname_is_valid=DataValidation.nameIsValid(firstName),
                        pesel_is_valid=DataValidation.peselIsValid(pesel, (sex=="M")),
                        address_is_valid=DataValidation.addressIsValid(address),
                        city_is_valid=DataValidation.cityIsValid(city);
                if(!fname_is_valid){firstNameField.setText("First name is invalid");}
                else{firstNameField.setText("ok");}
                if(!lname_is_valid){lastNameField.setText("Last name is invalid");}
                else{lastNameField.setText("ok");}
                if(!address_is_valid){addressField.setText("Address is invalid");}
                else{addressField.setText("ok");}
                if(!city_is_valid){cityField.setText("City is invalid");}
                else{cityField.setText("ok");}
                if(!pesel_is_valid){peselField.setText("PESEL is invalid");}
                else{peselField.setText("ok");}
                if(fname_is_valid&&lname_is_valid&&address_is_valid&&city_is_valid&&pesel_is_valid){

                    //Database.addUser( user.username, user.password, user.email);
                    //Database.addUserData( user.username, user.firstName, user.lastName, user.sex, user.city, user.address, user.pesel);

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
                    prev_screen.frame.setVisible(true);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {counter=0;}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {counter=0;}
            @Override
            public void mouseEntered(MouseEvent e) {counter=0;}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        new Thread() {
            public void run() {
                while (counter <= 120) {
                    if(!frame.isDisplayable()){counter=0;}
                    else {
                        timerLabel.setText("Time before log out: " + (120 - counter++));
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
                frame.dispose();
            }
        }.start();
        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        User user = new User();
        user.username = "test_user";
        user.password = "password";
        user.email = "maks.ovsienko2@gmail.com";
        new RegistrationScreen2(user,null,new Screen()).CreateScreen();
    }

}
