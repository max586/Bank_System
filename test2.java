import java.awt.*;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;

public class test2{

    public static void main(String args[]) {
        JFrame f = new JFrame("GridBag Layout Example");
        f.setSize(400,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton b1=new JButton("button1"),b2=new JButton("button2"),b3=new JButton("button3"),b4=new JButton("button4");
        GridBagConstraints gbc = new GridBagConstraints();
        f.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5,0,5,0);

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=2;
        gbc.gridwidth=1;
        gbc.fill = GridBagConstraints.VERTICAL;
        f.add(b1,gbc);

        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        f.add(b2,gbc);

        gbc.gridx=2;
        gbc.gridy=0;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        f.add(b3,gbc);

        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridheight=1;
        gbc.gridwidth=2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        f.add(b4,gbc);

        f.setVisible(true);
    }
}
