package src;

import src.Database;
import src.mainFrame.MainFrame;
import src.transfers.*;
import  src.mainPanel.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws IOException, FontFormatException {
        new TransferFactory(AccountChoosed.ORDINARYACCOUNT, new User(), new MainFrame()).getTransfer(TransferFactory.TransferType.ZAGRANICZNY);
    }
}
