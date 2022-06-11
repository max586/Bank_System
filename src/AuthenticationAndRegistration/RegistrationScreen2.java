package src.AuthenticationAndRegistration;

import src.DataValidation;
import src.Database;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;

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
    public JTextField streetField;
    public JComboBox sexComboBox;
    public JTextField peselField;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel firstNameLabel;
    public JLabel lastNameLabel;
    public JLabel cityLabel;
    public JLabel streetLabel;
    public JLabel peselLabel;
    public JLabel sexLabel;
    public JPanel panel;
    public JLabel timeLabel;
    public JLabel phoneNrLabel;
    public JTextField phoneNrField;
    public JTextField streetNrField;
    public JLabel streetNrLabel;
    public JLabel postCodeLabel;
    public JTextField postCodeField;
    public int counter=0;

    public RegistrationScreen2(){}
    public RegistrationScreen2(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        frame.setTitle("Second registration Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                String firstName=firstNameField.getText();
                String lastName=lastNameField.getText();
                String sex=(String)sexComboBox.getSelectedItem();
                String phone_nr=phoneNrField.getText();
                String pesel=peselField.getText();
                String city = cityField.getText();
                String post_code = postCodeField.getText();
                String street = streetField.getText();
                String street_nr = streetNrField.getText();
                boolean lname_is_valid= DataValidation.nameIsValid(lastName), fname_is_valid=DataValidation.nameIsValid(firstName),
                        pesel_is_valid=DataValidation.peselIsValid(pesel, (sex=="M")),post_code_is_valid=DataValidation.postcodeIsValid(post_code),
                        city_is_valid=DataValidation.cityIsValid(city),street_is_valid=DataValidation.cityIsValid(street),street_nr_is_valid=DataValidation.streetNrIsValid(street_nr),
                        phone_nr_is_valid=DataValidation.phoneNrIsValid(phone_nr);
                if(!fname_is_valid){firstNameField.setText("First name is invalid");}
                else{firstNameField.setText("ok");}
                if(!lname_is_valid){lastNameField.setText("Last name is invalid");}
                else{lastNameField.setText("ok");}
                if(!phone_nr_is_valid){phoneNrField.setText("Phone nr is invalid");}
                else{phoneNrField.setText("ok");}
                if(!city_is_valid){cityField.setText("City is invalid");}
                else{cityField.setText("ok");}
                if(!post_code_is_valid){postCodeField.setText("Post code is invalid");}
                else{postCodeField.setText("ok");}
                if(!street_is_valid){streetField.setText("Street is invalid");}
                else{streetField.setText("ok");}
                if(!street_nr_is_valid){streetNrField.setText("Street number is invalid");}
                else{streetNrField.setText("ok");}
                if(!pesel_is_valid){peselField.setText("PESEL is invalid");}
                else{peselField.setText("ok");}
                if(fname_is_valid&&lname_is_valid&&phone_nr_is_valid&&city_is_valid&&post_code_is_valid&&street_is_valid&&street_nr_is_valid&&pesel_is_valid){

                    user.firstName=firstName;
                    user.lastName=lastName;
                    user.sex=sex;
                    user.phone_number=phone_nr;
                    user.city=city;
                    user.post_code=post_code;
                    user.street=street;
                    user.street_nr=street_nr;
                    user.pesel=pesel;

                    Database.addUser( user.username, user.password, user.email,user.appCode);
                    Database.addUserData( user.username, user.firstName, user.lastName, user.sex, user.phone_number,user.city,user.post_code,user.street, user.street_nr, user.pesel);

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

        frame.setSize(1000,800);
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
