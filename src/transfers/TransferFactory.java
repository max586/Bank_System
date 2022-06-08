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
    public AccountChoosed accountChoosed;
    public User user;
    public MainFrame frame;

    public TransferFactory(AccountChoosed accountChoosed1, User user1,MainFrame mainFrame){
        accountChoosed = accountChoosed1;
        user = user1;
        frame = mainFrame;
    }

    public Transfer getTransfer(TransferType transferType) throws IOException, FontFormatException {
        return switch (transferType) {
            case KRAJOWY -> new StandardTransfer(accountChoosed,user,frame);
            case ZAGRANICZNY -> new ForeignStandardTransfer(accountChoosed, user,frame);
            case ZLECENIESTALE -> new StandingOrder(accountChoosed,user,frame);
            case TELEFONBLIK -> new BlikPhoneTransfer(accountChoosed,user,frame);
            case WLASNY -> new OwnTransfer(user,frame);
        };
    }
}

