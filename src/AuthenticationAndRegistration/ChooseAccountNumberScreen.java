package src.AuthenticationAndRegistration;

import src.MainScreen;
import src.Screen;
import src.User;
import src.timer.AppTimer;
import src.timer.MouseAction;
import src.transfers.AccountChoosed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChooseAccountNumberScreen extends Screen{
    public JPanel panel;
    public JRadioButton ordinaryAccountRadioButton;
    public JRadioButton savingsAccountRadioButton;
    public JLabel descrLabel;
    public JButton submitButton;
    public JButton returnButton;
    public JButton exitButton;
    public JLabel timeLabel;
    public int counter=0;
    public ChooseAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        frame.setTitle("Choose account number Screen");
        frame.setContentPane(panel);
        AppTimer appTimer = new AppTimer(timeLabel,this);
        panel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ordinaryAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen(), AccountChoosed.ORDINARYACCOUNT).CreateScreen();
                    }
                }
                else if(savingsAccountRadioButton.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen(),AccountChoosed.SAVINGSACCOUNT).CreateScreen();
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
