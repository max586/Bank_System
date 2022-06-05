package transfers;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class TransferFactory {
    public enum TransferType {
        KRAJOWY, ZAGRANICZNY, ZLECENIESTALE, TELEFONBLIK, WLASNY;
    }
    private MainFrame frame;
    private Map<String,String> senderData;
    double senderAmount;

    public TransferFactory(MainFrame mainFrame, Map<String,String> senderData1, double senderAmount1){
        frame = mainFrame;
        senderData = senderData1;
        senderAmount = senderAmount1;
    }
    public Transfer getTransfer(TransferType transferType) throws IOException, FontFormatException {
        return switch (transferType) {
            case KRAJOWY -> new StandardTransfer(frame, senderData, senderAmount);
            case ZAGRANICZNY -> new ForeignStandardTransfer(frame, senderData, senderAmount);
            case ZLECENIESTALE -> new StandingOrder(frame, senderData, senderAmount);
            case TELEFONBLIK -> new BlikPhoneTransfer(frame, senderData, senderAmount);
            case WLASNY -> new OwnTransfer(frame,senderData,senderAmount);
        };
    }
}
