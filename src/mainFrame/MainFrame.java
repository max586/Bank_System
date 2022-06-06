package mainFrame;

import javax.swing.*;
public class MainFrame {
    private JFrame jFrame;
    public MainFrame(){
        jFrame = new JFrame();
        jFrame.setSize(1080,720);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JFrame getjFrame(){
        return jFrame;
    }
}