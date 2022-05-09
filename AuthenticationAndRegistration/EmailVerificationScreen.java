package AuthenticationAndRegistration;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Random;

public class EmailVerificationScreen {
    static int number_of_attempts=5;

    public static void CreateScreen(User user, String code){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Email verification");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.HORIZONTAL;

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
                    RegistrationScreen2.CreateScreen(user);
                }
                else{
                    number_of_attempts--;
                    if(number_of_attempts>0){
                        JOptionPane.showMessageDialog(frame, "Wrong code! "+number_of_attempts+" attempts left","Warning",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        frame.dispose();
                    }
                }
            }
        });
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.insets = new Insets(5,5,5,5);
        frame.add(submit, gbc);

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

    public static void main(User user) {   
        String code = generateCode(6);
        try {
            JavaMail.SendMail(user.email, code);
        } catch (Exception e) {
            //TODO: handle exception
        }
        CreateScreen(user,code);
    }    
}
