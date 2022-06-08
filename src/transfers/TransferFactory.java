package src.transfers;

import src.mainFrame.MainFrame;
import src.User;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class TransferFactory {
    public enum TransferType {
        KRAJOWY, ZAGRANICZNY, ZLECENIESTALE, TELEFONBLIK, WLASNY;
    }
    public User user;
    public MainFrame frame;
    public Map<String,String> senderData;

    public TransferFactory(User user1,MainFrame mainFrame, Map<String,String> senderData1){
        user = user1;
        frame = mainFrame;
        senderData = senderData1;
    }
    public Transfer getTransfer(TransferType transferType) throws IOException, FontFormatException {
        return switch (transferType) {
            case KRAJOWY -> new StandardTransfer(user,frame, senderData);
            case ZAGRANICZNY -> new ForeignStandardTransfer(user,frame, senderData);
            case ZLECENIESTALE -> new StandingOrder(user,frame, senderData);
            case TELEFONBLIK -> new BlikPhoneTransfer(user,frame, senderData);
            case WLASNY -> new OwnTransfer(user,frame,senderData);
        };
    }
}

