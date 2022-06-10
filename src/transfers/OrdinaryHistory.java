package src.transfers;

import src.Database;
import src.MainScreen;
import src.Screen;
import src.User;
import src.mainFrame.MainFrame;
import src.timer.AppTimer;
import src.timer.MouseAction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class OrdinaryHistory {
    private JPanel ordinaryPanel;
    private JLabel panelTitleLabel;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JButton cancelButton;
    private JTable historyTable;
    private JPanel historyPanel;
    private JTable OutcomingHistoryTable;
    private JScrollPane OutcomingHistoryPane;
    private JTable IncomingHistoryTable;
    private JScrollPane IncomingHistoryPane;
    private MainFrame frame;
    public OrdinaryHistory(MainFrame mainFrame, User user) throws SQLException {
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        ordinaryPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        String[][]data = Database.getHistoryFrom("HistoryOrdinary",user.username);
        String[] cols = {"Operation Date","Transfer Type", "Account nr from", "Account nr to", "Phone nr to",
        "Transfer amount","Transfer currency","Total Cost","Transfer title","Start date","End date",
                "Transfer cycle","Cycle units","Receiver name","Receiver surname","Receiver Town","Receiver Street","Receiver Street nr"};
        OutcomingHistoryTable.setModel(new DefaultTableModel(data,cols));
        OutcomingHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        OutcomingHistoryPane.createHorizontalScrollBar();
        OutcomingHistoryPane.createVerticalScrollBar();
        OutcomingHistoryPane.setVisible(true);
        OutcomingHistoryTable.setVisible(true);
        String[][]dataincoming = Database.getHistoryTo("HistoryOrdinary",user.username);
        String[] colsincoming = {"Operation Date","Transfer Type", "Account nr from", "Account nr to", "Phone nr to",
                "Transfer amount","Transfer currency","Total Cost","Transfer title","Start date","End date",
                "Transfer cycle","Cycle units","Receiver name","Receiver surname","Receiver Town","Receiver Street","Receiver Street nr"};
        IncomingHistoryTable.setModel(new DefaultTableModel(dataincoming,colsincoming));
        IncomingHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        IncomingHistoryPane.createHorizontalScrollBar();
        IncomingHistoryPane.createVerticalScrollBar();
        IncomingHistoryPane.setVisible(true);
        IncomingHistoryTable.setVisible(true);

        frame.getjFrame().setContentPane(ordinaryPanel);
        frame.getjFrame().setVisible(true);
        cancelButton.addActionListener(e->{
            frame.getjFrame().dispose();
            new MainScreen(user,null,new Screen()).CreateScreen();        });
    }
}
