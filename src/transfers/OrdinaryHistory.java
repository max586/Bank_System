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
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.sql.SQLException;

public class OrdinaryHistory {
    private JPanel ordinaryPanel;
    private JLabel panelTitleLabel;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JButton cancelButton;
    private JTable historyTable;
    private JPanel historyPanel;
    private JTable transferHistoryTable;
    private JScrollPane transferHistoryScroll;
    private MainFrame frame;
    public OrdinaryHistory(MainFrame mainFrame, User user) throws SQLException {
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        ordinaryPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        //String[][]data = Database.getHistoryFrom("HistoryOrdinary",user.username);
        String[][]data = Database.getHistoryFrom("HistoryOrdinary","test_user");
        String[] cols = {"Operation Date","Transfer Type", "Account nr from", "Account nr to", "Phone nr to",
        "Transfer amount","Transfer currency","Total Cost","Transfer title","Start date","End date",
                "Transfer cycle","Cycle units","Receiver name","Receiver surname","Receiver Town","Receiver Street","Receiver Street nr"};

        transferHistoryTable.setModel(new DefaultTableModel(data,cols));
        transferHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        transferHistoryScroll.createHorizontalScrollBar();
        transferHistoryScroll.createVerticalScrollBar();
        transferHistoryScroll.setVisible(true);
        transferHistoryTable.setVisible(true);
        frame.getjFrame().setContentPane(ordinaryPanel);
        frame.getjFrame().setVisible(true);
        cancelButton.addActionListener(e->{
            frame.getjFrame().dispose();
            new MainScreen(user,null,new Screen()).CreateScreen();        });
    }
}
