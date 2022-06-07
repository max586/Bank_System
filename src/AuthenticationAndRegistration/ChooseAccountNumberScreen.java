package src.AuthenticationAndRegistration;

import src.MainScreen;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseAccountNumberScreen extends Screen{
    public JPanel panel;
    public JRadioButton ordinaryAccountRadioButton;
    public JRadioButton savingsAccountRadioButton;
    public JLabel descrLabel;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public ChooseAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //warning! duplicated code!!!
                if(ordinaryAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen(), "ordinary").CreateScreen();
                    }
                }
                else if(savingsAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen(),"saving").CreateScreen();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "account wasn't chosen!");
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

        frame.setSize(800,600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        User test_user = new User();
        test_user.username = "test_user";
        test_user.password = "password";
        test_user.email = "maks.ovsienko2@gmail.com";
        new ChooseAccountNumberScreen(test_user,null,new Screen()).CreateScreen();
    }
}
