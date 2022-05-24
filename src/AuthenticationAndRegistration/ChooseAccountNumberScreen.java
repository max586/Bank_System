package src.AuthenticationAndRegistration;

import src.Screen;
import src.MainScreen;
import src.User;
import javax.swing.*;
import java.awt.Insets;
import java.awt.event.*;


public class ChooseAccountNumberScreen extends Screen{
    JLabel descr;
    JRadioButton ordinaryAccountChoice;
    JRadioButton savingsAccountChoice;
    JButton submitButton;

    public ChooseAccountNumberScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        super.CreateScreen();
        frame.setTitle("Choose account number screen");

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        descr = new JLabel("Choose your account");
        gbc.insets = new Insets(5,5,5,5);
        frame.add(descr,gbc);

        ordinaryAccountChoice = new JRadioButton("Ordinary account");
        savingsAccountChoice = new JRadioButton("Savings account");
        ButtonGroup group = new ButtonGroup();
        group.add(ordinaryAccountChoice);
        group.add(savingsAccountChoice);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,5,5,10);     
        frame.add(ordinaryAccountChoice,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.insets = new Insets(5,10,5,5);
        frame.add(savingsAccountChoice,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=2;
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //warning! duplicated code!!!
                if(ordinaryAccountChoice.isSelected()){
                    frame.dispose();
                    if(next_screen!=null){
                        new MainScreen(user, ChooseAccountNumberScreen.this, new Screen()).CreateScreen();
                    }
                }
                else if(savingsAccountChoice.isSelected()){
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
        gbc.insets = new Insets(5,5,5,5);
        frame.add(submitButton,gbc);

        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(returnButton,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
}
