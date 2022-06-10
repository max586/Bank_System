package src.transfers;

import src.Database;
import src.MainScreen;
import src.Screen;
import src.User;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import java.sql.SQLException;

public class OrdinaryHistory {
    private JPanel ordinaryPanel;
    private JLabel panelTitleLabel;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JScrollPane scrollPane;
    private JButton cancelButton;
    private MainFrame frame;
    public OrdinaryHistory(MainFrame mainFrame, User user) throws SQLException {
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        ordinaryPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        DefaultListModel<String> dlm = new DefaultListModel<String>();
        JList<String> list = new JList<>(dlm);
        String[][]data = Database.getHistoryOrdinary("HistoryOrdinary",user.username);
        for(int i=0;i<data.length;++i){
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<data[0].length;++j){
                sb.append(data[i][j]).append(" ");
            }
            dlm.addElement(sb.toString());
        }
        scrollPane.add(list);
        frame.getjFrame().setContentPane(ordinaryPanel);
        frame.getjFrame().setVisible(true);
        cancelButton.addActionListener(e->{
            frame.getjFrame().dispose();
new MainScreen(user,null,new Screen()).CreateScreen();        });
    }
}
