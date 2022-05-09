package AuthenticationAndRegistration;

import src.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;


public class ChooseAccountNumberScreen {
    static JLabel descr;
    static JRadioButton ordinaryAccountChoice;
    static JRadioButton savingsAccountChoice;
    static JButton submitButton;
    static JButton exitButton;
    public static void CreateScreen(User user){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Authentification screen");

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

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
                if(ordinaryAccountChoice.isSelected()){
                    frame.dispose();
                    MainScreen.CreateScreen(user);
                }
                else if(savingsAccountChoice.isSelected()){
                    frame.dispose();
                    MainScreen.CreateScreen(user);
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
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                        frame.dispose();  
                    }  
        }); 
        gbc.insets = new Insets(5,5,5,5);
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
}
