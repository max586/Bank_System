package src.transfers;

import src.mainFrame.MainFrame;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class PdfFactory {
    public enum PdfType {
        STANDARD, BLIK, ZLECENIESTALE, WLASNY;
    }

    private String generationDate;
    private Map<String, String> senderData;
    private Map<String, String> receiverData;
    private Map<String, String> transferData;

    public PdfFactory(String generationDate1, Map<String, String> senderData1, Map<String, String> receiverData1, Map<String, String> transferData1) {
        generationDate = generationDate1;
        senderData = senderData1;
        receiverData = receiverData1;
        transferData = transferData1;
    }

    public PdfGenerator getPdfGenerator(PdfType pdfType) throws IOException, FontFormatException {
        return switch (pdfType) {
            case STANDARD -> new PdfGeneratorStandard(generationDate, senderData, receiverData, transferData);
            case BLIK -> new PdfGeneratorBLIK(generationDate, senderData, receiverData, transferData);
            case ZLECENIESTALE -> new PdfGeneratorStandingOrder(generationDate, senderData, receiverData, transferData);
            case WLASNY -> new PdfGeneratorOwn(generationDate, senderData, receiverData, transferData);
        };
    }
}

