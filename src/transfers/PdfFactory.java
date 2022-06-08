package src.transfers;

import src.User;
import src.mainFrame.MainFrame;

import javax.sound.midi.Receiver;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class PdfFactory {
    public enum PdfType {
        STANDARD, BLIK, ZLECENIESTALE, WLASNY;
    }
    public String generationDate;
    public User user;
    public AccountChoosed accountChoosedUser;
    public User receiver;
    public AccountChoosed accountChoosedReceiver;
    public Map<String, String> transferData;

    public PdfFactory(String generationDate1, User user1, AccountChoosed accountChoosed1, User receiver1, AccountChoosed accountChoosed2, Map<String,String> transferData1) {
        generationDate = generationDate1;
        user = user1;
        accountChoosedUser = accountChoosed1;
        receiver = receiver1;
        accountChoosedReceiver = accountChoosed2;
        transferData = transferData1;
    }

    public PdfGenerator getPdfGenerator(PdfType pdfType) throws IOException, FontFormatException {
        return switch (pdfType) {
            case STANDARD -> new PdfGeneratorStandard(generationDate, user,accountChoosedUser,receiver,accountChoosedReceiver, transferData);
            case BLIK -> new PdfGeneratorBLIK(generationDate, user,receiver, transferData);
            case ZLECENIESTALE -> new PdfGeneratorStandingOrder(generationDate, user,accountChoosedUser,receiver,accountChoosedReceiver, transferData);
            case WLASNY -> new PdfGeneratorOwn(generationDate, user,accountChoosedUser,receiver,accountChoosedReceiver, transferData);
        };
    }
}

