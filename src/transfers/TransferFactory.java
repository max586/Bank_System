package transfers;

import mainFrame.MainFrame;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class TransferFactory {
    public enum TransferType {
        KRAJOWY, ZAGRANICZNY, ZLECENIESTALE, TELEFONBLIK, WLASNY;
    }
    private MainFrame frame;
    private Map<String,String> senderData;

    public TransferFactory(MainFrame mainFrame, Map<String,String> senderData1){
        frame = mainFrame;
        senderData = senderData1;
    }
    public Transfer getTransfer(TransferType transferType) throws IOException, FontFormatException {
        return switch (transferType) {
            case KRAJOWY -> new StandardTransfer(frame, senderData);
            case ZAGRANICZNY -> new ForeignStandardTransfer(frame, senderData);
            case ZLECENIESTALE -> new StandingOrder(frame, senderData);
            case TELEFONBLIK -> new BlikPhoneTransfer(frame, senderData);
            case WLASNY -> new OwnTransfer(frame,senderData);
        };
    }
}
