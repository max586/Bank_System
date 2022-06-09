package src.AuthenticationAndRegistration;

import src.MainScreen;
import src.Screen;
import src.User;

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
    public JLabel timerLabel;
    public int counter=0;
    public ChooseAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    public void CreateScreen() {
        frame.setTitle("Choose account number Screen");
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
        User test_user = new User();
        test_user.username = "test_user";
        test_user.password = "password";
        test_user.email = "maks.ovsienko2@gmail.com";
        new ChooseAccountNumberScreen(test_user,null,new Screen()).CreateScreen();
    }
}
