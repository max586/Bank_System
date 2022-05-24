package src.AuthenticationAndRegistration;

import src.Screen;
import src.User;
import src.JavaMail;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Insets;
import java.util.Random;

public class EmailVerificationScreen extends Screen{
    public int number_of_attempts=5;
    public boolean isEmailVerifed;

    public EmailVerificationScreen(User user, Screen prev_screen, Screen next_screen){
        super(user,prev_screen,next_screen);
    }
    @Override
    public void CreateScreen(){
        //sending a code on email
        String code = generateCode(6);
        try {
            JavaMail.SendMail(user.email, code);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("failed to send an email");
            System.out.println(e);
        }

        super.CreateScreen();
        frame.setTitle("Email verification screen");
        JLabel text = new JLabel("<html>We've sent 6-digit verification code on your email address.<br> Enter it in the text field below</html>");        
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(text,gbc);

        JTextField codeField = new JTextField();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(codeField,gbc);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(codeField.getText().equals(code)){
                    JOptionPane.showMessageDialog(frame, "Well done!!!");
                    frame.dispose();
                    if(next_screen!=null){
                        frame.dispose();
                        next_screen.user=user;
                        next_screen.prev_screen = EmailVerificationScreen.this;
                        next_screen.next_screen = new Screen();
                        next_screen.CreateScreen();
                    }
                }
                else{
                    number_of_attempts--;
                    if(number_of_attempts>0){
                        JOptionPane.showMessageDialog(frame, "Wrong code! "+number_of_attempts+" attempts left","Warning",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        frame.dispose();
                        if(prev_screen!=null){
                            prev_screen.CreateScreen();
                        }
                    }
                }
            }
        });
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(submit, gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=2;
        frame.add(returnButton,gbc);

        gbc.gridx=0;
        gbc.gridy=5;
        frame.add(exitButton,gbc);

        frame.pack();
        frame.setVisible(true);
    }
    public static String generateCode(int length){
        String code="";
        Random rnd = new Random();
        for(int i=0;i<length;i++){
            code+=Integer.toString(rnd.nextInt(9));
        }
        return code;
    };

    public static void main(User user, boolean option) {   
        
        //CreateScreen(user,code, option);
    }    
}
