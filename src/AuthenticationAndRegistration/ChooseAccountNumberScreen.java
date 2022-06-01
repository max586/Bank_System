package src.AuthenticationAndRegistration;

import src.MainScreen;
import src.Screen;
import src.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseAccountNumberScreen extends Screen{
    private JPanel panel;
    private JRadioButton ordinaryAccountRadioButton;
    private JRadioButton savingsAccountRadioButton;
    private JLabel descrLabel;
    private JButton submitButton;
    private JButton returnButton;
    private JButton exitButton;
    public ChooseAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        super.CreateScreen();
        frame.setContentPane(panel);

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //warning! duplicated code!!!
                if(ordinaryAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen()).CreateScreen();
                    }
                }
                else if(savingsAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen()).CreateScreen();
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
        User test_user = new User();
        test_user.username = "test_user";
        test_user.password = "password";
        test_user.email = "maks.ovsienko2@gmail.com";
        new ChooseAccountNumberScreen(test_user,null,new Screen()).CreateScreen();
    }
}
