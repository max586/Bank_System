package src;

import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;

public class FAQScreen {
    private JLabel panelTitleLabel;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JButton cancelButton;
    private JPanel faqPanel;
    //private MainFrame frame;

    public FAQScreen(User user, MainFrame frame){
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        faqPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        cancelButton.addActionListener(e->
        {
            frame.getjFrame().dispose();
            new MainScreen(user,null,new Screen()).CreateScreen();        });
        faqPanel.revalidate();
        frame.getjFrame().revalidate();
        frame.getjFrame().setContentPane(faqPanel);
        frame.getjFrame().setVisible(true);
    }
}
