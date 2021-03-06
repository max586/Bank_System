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

public class SavingsHistory {
        public JPanel ordinaryPanel ;
        public JLabel panelTitleLabel;
        public JPanel timerPanel;
        public JLabel timeLabel;
        public JScrollPane scrollPane;
        public JButton cancelButton;
    public JTable outgoingHistoryTable;
    public JScrollPane outgoingHistoryPane;
    public JScrollPane IncomingHistoryPane;
    public JTable IncomingHistoryTable;
    public MainFrame frame;
        public SavingsHistory(MainFrame mainFrame, User user) throws SQLException {
            outgoingHistoryTable.setEnabled(false);
            IncomingHistoryTable.setEnabled(false);

            frame = mainFrame;
            AppTimer appTimer = new AppTimer(timeLabel,frame);
            ordinaryPanel.addMouseMotionListener(new MouseAction(appTimer));
            appTimer.start();
            String[][]dataoutcoming = Database.getHistoryFrom("HistorySavings",user.username);
            String[] colsoutcoming = {"Operation Date","Transfer Type", "Account nr to", "Phone nr to",
                    "Transfer amount","Transfer currency","Total Cost","Transfer title","Start date","End date",
                    "Transfer cycle","Cycle units","Receiver name","Receiver surname","Receiver Town","Receiver Street","Receiver Street nr"};
            outgoingHistoryTable.setModel(new DefaultTableModel(dataoutcoming,colsoutcoming));
            outgoingHistoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            outgoingHistoryPane.createHorizontalScrollBar();
            outgoingHistoryPane.createVerticalScrollBar();
            outgoingHistoryPane.setVisible(true);
            outgoingHistoryTable.setVisible(true);
            String[][]dataincoming2 = Database.getHistoryTo("HistorySavings",user.username);
            String[] colsincoming2 = {"Operation Date","Transfer Type", "Account nr from", "Phone nr to",
                    "Transfer amount","Transfer currency","Total Cost","Transfer title","Start date","End date",
                    "Transfer cycle","Cycle units","Receiver name","Receiver surname","Receiver Town","Receiver Street","Receiver Street nr"};
            IncomingHistoryTable.setModel(new DefaultTableModel(dataincoming2,colsincoming2));
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
