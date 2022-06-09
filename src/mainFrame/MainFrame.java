package src.mainFrame;

import src.PreScreen;

import javax.swing.*;
public class MainFrame extends PreScreen {
    public MainFrame(){
        frame = new JFrame();
        frame.setSize(1080,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JFrame getjFrame(){
        return frame;
    }
}