package src.mainPanel;
import src.mainFrame.MainFrame;
import src.transfers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainPanel {
    public JPanel mainPanel;
    public JLabel panelTitleLabel;
    public MainFrame frame;
    public MainPanel(MainFrame mainFrame){
        frame = mainFrame;
        frame.getjFrame().setContentPane(mainPanel);
        frame.getjFrame().setVisible(true);
    }
}
