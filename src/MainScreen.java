package src;

import src.Settings.SettingsMainScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.Random;

public class MainScreen extends Screen{
    private JPanel panel;
    private JButton createCardButton;
    private JButton settingsButton;
    private JButton returnButton;
    private JButton exitButton;
    public MainScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        super.CreateScreen();
        frame.setContentPane(panel);
        createCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Statement st = Database.connectToDatabase("bank_system","root","password");
                if(Database.hasCard(st,user.username)){
                    JOptionPane.showMessageDialog(frame,"Your already have a card!");
                }
                else{
                    user.card_number=generateCardNumber();
                    user.pin_code = generatePIN();
                    Database.addCard(st,user.username,user.card_number,user.pin_code);
                    JOptionPane.showMessageDialog(frame,"<html>Your card is ready!<br>Card nr: "+user.card_number+"<br>PIN: "+user.pin_code+"<br>You can get it at our bank</html>");
                }
            }
        });
        settingsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(next_screen!=null){
                    frame.dispose();
                    new SettingsMainScreen(user, MainScreen.this, new Screen()).CreateScreen();
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
    public String generateCardNumber(){
        String nr="";
        Random rnd = new Random();
        for(int i=0;i<16;i++){nr+=Integer.toString(rnd.nextInt(10));}
        return nr;
    }
    public String generatePIN(){
        String pin="";
        Random rnd = new Random();
        for(int i=0;i<4;i++){pin+=Integer.toString(rnd.nextInt(10));}
        return pin;
    }

    public static void main(String[] args) {
        User test_user = new User();
        test_user.username="test_user";
        new MainScreen(test_user,null,null).CreateScreen();
    }
}
