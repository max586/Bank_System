package src;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;

public class Screen {
    public User user;
    public Screen prev_screen;
    public Screen next_screen;
    public JFrame frame;
    public GridBagConstraints gbc;
    public JButton returnButton;
    public JButton exitButton;
    public Screen(){}
    public Screen(User user, Screen prev_screen, Screen next_screen){
        this.user = user;
        this.prev_screen = prev_screen;
        this.next_screen = next_screen;
    }
    public void CreateScreen(){
        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx=0.5;
        gbc.weighty=0.5;

        exitButton = new JButton("exit");
        exitButton.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){  
                        frame.dispose();  
                    }  
        }); 
        
        returnButton = new JButton("return");
        returnButton.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){  
                        frame.dispose();
                        if(prev_screen!=null){
                            prev_screen.CreateScreen();
                        }
                    }  
        }); 
    }
}
