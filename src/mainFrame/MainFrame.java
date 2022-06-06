package src.mainFrame;

import javax.swing.*;
public class MainFrame {
    public JFrame jFrame;
    public MainFrame(){
        jFrame = new JFrame();
        jFrame.setSize(1080,720);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JFrame getjFrame(){
        return jFrame;
    }
}