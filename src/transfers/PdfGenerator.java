package src.transfers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import src.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public interface PdfGenerator{
    public void generatePDF(String directory) throws IOException;
}

class PdfGeneratorStandard implements PdfGenerator {
    protected final String title = "Transfer Confirmation";
    protected final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    protected final LocalDateTime now = LocalDateTime.now();
    protected final String generationDate = dtf.format(now);
    protected Map<String,String> data;
    protected PDDocument pdfDocument;
    protected PDPage pdfPage;
    protected PDFont titleFont;
    protected PDFont titlesFont;
    protected PDFont dataFont;
    protected PDPageContentStream contentStream;
    protected String fileName;
    protected String userAccountNumber;
    protected String receiverAccountNumber;
    protected float titleFontSize = 20;
    protected float dateFontSize = 14;
    protected float dataFontSize = 12;

    public PdfGeneratorStandard(){}
    public PdfGeneratorStandard(String operationDate, User user1, AccountChoosed accountChoosed1, User receiver1, AccountChoosed accountChoosed2, Map<String,String> transferData){
        fileName = "Confirmation_"+transferData.get("typ")+"_"+user1.firstName+user1.lastName+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Transfer type: ",transferData.get("typ"));
        data.put("Posting date: ", operationDate);
        data.put("Transfer date: ", operationDate);
        if(accountChoosed1==AccountChoosed.ORDINARYACCOUNT) userAccountNumber = user1.ordinary_account_number;
        else userAccountNumber = user1.savings_account_number;
        if(accountChoosed2==AccountChoosed.ORDINARYACCOUNT) receiverAccountNumber = receiver1.ordinary_account_number;
        else receiverAccountNumber = receiver1.savings_account_number;
        data.put("From account number: ",userAccountNumber);
        String sender = "";
        sender+=user1.firstName;
        sender = sender+" "+user1.lastName;
        if(user1.city!=null){
            sender = sender+" "+user1.city;
            sender = sender+" "+user1.post_code;
            sender = sender+" "+"ul."+user1.street;
            sender = sender+" "+user1.street_nr;
        }
        data.put("Paymaster: ",sender);
        data.put("To account number: ",receiverAccountNumber);
        String receiver = "";
        receiver+=receiver1.firstName;
        receiver = receiver+" "+receiver1.lastName;
        if(receiver1.city!=null){
            receiver = receiver+" "+receiver1.city;
            receiver = receiver+" "+receiver1.post_code;
            receiver = receiver+" "+"ul."+receiver1.street;
            receiver = receiver+" "+receiver1.street_nr;
        }
        data.put("Receiver: ",receiver);
        data.put("Transfer amount: ",transferData.get("kwota")+" "+transferData.get("waluta"));
        data.put("Amount booked: ", "-" + String.format("%.2f",Double.parseDouble(transferData.get("kwotaPLN")))+" "+"PLN");
        data.put("Title: ",transferData.get("tytul"));
    }

    public void generatePDF(String directory) throws IOException {
        pdfDocument = new PDDocument();
        pdfPage = new PDPage();
        pdfDocument.addPage(pdfPage);
        titleFont = PDType0Font.load( pdfDocument, new File( "LeagueSpartan-2.220/static/TTF/LeagueSpartan-Bold.ttf" ) );
        titlesFont = PDType0Font.load( pdfDocument, new File( "LeagueSpartan-2.220/static/TTF/LeagueSpartan-SemiBold.ttf" ) );
        dataFont = PDType0Font.load( pdfDocument, new File( "LeagueSpartan-2.220/static/TTF/LeagueSpartan-Light.ttf" ) );
        contentStream = new PDPageContentStream(pdfDocument, pdfPage);

        float leading = 2*titleFontSize;
        float yCoordinate = pdfPage.getCropBox().getUpperRightY() - 70;
        float startX = pdfPage.getCropBox().getLowerLeftX() + 40;
        float endX = pdfPage.getCropBox().getUpperRightX() - 40;

        contentStream.setFont(titleFont,titleFontSize);
        contentStream.beginText();
        contentStream.newLineAtOffset(startX, yCoordinate);
        contentStream.showText(title);
        yCoordinate -= titleFontSize;
        contentStream.newLineAtOffset(0, -leading);
        contentStream.setFont(titlesFont,dateFontSize);
        yCoordinate -= leading;
        contentStream.showText("Generation date: ");
        contentStream.setFont(dataFont,dateFontSize);
        contentStream.showText(generationDate);
        contentStream.endText(); // End of text mode
        yCoordinate += 10;
        contentStream.moveTo(startX, yCoordinate);

        contentStream.lineTo(endX, yCoordinate);
        contentStream.stroke();
        yCoordinate -= titleFontSize;
        int i=0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            contentStream.setFont(titlesFont,dataFontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX+titleFontSize, yCoordinate);
            contentStream.showText(entry.getKey());
            contentStream.setFont(dataFont,dataFontSize);
            contentStream.showText(entry.getValue());
            contentStream.endText();
            yCoordinate-=dataFontSize;
            contentStream.moveTo(startX+titleFontSize, yCoordinate);
            contentStream.setLineDashPattern (new float[]{5,3}, 0);
            if(i==data.size()-1) contentStream.setLineDashPattern (new float[]{}, 0);
            contentStream.lineTo(endX, yCoordinate);
            contentStream.stroke();
            yCoordinate-=titleFontSize;
            ++i;
        }
        contentStream.close();
        pdfDocument.save(directory+"/"+fileName);
        pdfDocument.close();
    }
}

class PdfGeneratorStandingOrder extends PdfGeneratorStandard implements PdfGenerator{
    public PdfGeneratorStandingOrder(String operationDate, User user1, AccountChoosed accountChoosed1, User receiver1, AccountChoosed accountChoosed2, Map<String,String> transferData) {
        fileName = "Confirmation_"+transferData.get("typ")+"_"+user1.firstName+user1.lastName+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Transfer type: ",transferData.get("typ"));
        data.put("Order date: ", operationDate);
        data.put("Order start date: ", transferData.get("startdata"));
        if(transferData.containsKey("enddata"))  data.put("Order end date: ", transferData.get("enddata"));
        if(accountChoosed1==AccountChoosed.ORDINARYACCOUNT) userAccountNumber = user1.ordinary_account_number;
        else userAccountNumber = user1.savings_account_number;
        if(accountChoosed2==AccountChoosed.ORDINARYACCOUNT) receiverAccountNumber = receiver1.ordinary_account_number;
        else receiverAccountNumber = receiver1.savings_account_number;
        data.put("From account number: ",userAccountNumber);
        String sender = "";
        sender+=user1.firstName;
        sender = sender+" "+user1.lastName;
        if(receiver1.city!=null){
            sender = sender+" "+user1.city;
            sender = sender+" "+user1.post_code;
            sender = sender+" "+"ul."+user1.street;
            sender = sender+" "+user1.street_nr;
        }
        data.put("Paymaster: ",sender);
        data.put("To account number: ",receiverAccountNumber);
        String receiver = "";
        receiver+=receiver1.firstName;
        receiver = receiver+" "+receiver1.lastName;
        if(receiver1.city!=null){
            receiver = receiver+" "+receiver1.city;
            receiver = receiver+" "+receiver1.post_code;
            receiver = receiver+" "+"ul."+receiver1.street;
            receiver = receiver+" "+receiver1.street_nr;
        }
        data.put("Receiver: ",receiver);
        data.put("Transfer amount: ",transferData.get("kwota")+" "+transferData.get("waluta"));
        data.put("Amount booked: ", "-" + String.format("%.2f",Double.parseDouble(transferData.get("kwotaPLN")))+" "+"PLN");
        data.put("Title: ",transferData.get("tytul"));
    }
}

class PdfGeneratorBLIK extends PdfGeneratorStandard implements PdfGenerator{
    public PdfGeneratorBLIK(String operationDate, User user1, User receiver1, Map<String,String> transferData){
        fileName = "Confirmation_"+"BLIK"+"_"+user1.firstName+user1.lastName+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Transfer type: ",transferData.get("typ"));
        data.put("Posting date: ", operationDate);
        data.put("Transfer date: ", operationDate);
        data.put("From account number: ",user1.ordinary_account_number);
        data.put("Paymaster: ",user1.firstName+" "+user1.lastName);
        data.put("To phone number: ",receiver1.phone_number);
        data.put("Receiver: ",receiver1.firstName+" "+receiver1.lastName);
        data.put("Transfer amount: ",transferData.get("kwota")+" "+"PLN");
        data.put("Amount booked: ","-"+transferData.get("kwota")+" "+"PLN");
        data.put("Title: ",transferData.get("tytul"));
    }
}

class PdfGeneratorOwn extends PdfGeneratorStandard implements PdfGenerator{
    public PdfGeneratorOwn(String operationDate, User user1, AccountChoosed accountChoosed1, User receiver1, AccountChoosed accountChoosed2, Map<String,String> transferData){
        fileName = "Confirmation_"+transferData.get("typ")+"_"+user1.firstName+user1.lastName+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Transfer type: ",transferData.get("typ"));
        data.put("Posting date: ", operationDate);
        data.put("Transfer date: ", operationDate);
        if(accountChoosed1==AccountChoosed.ORDINARYACCOUNT){
            userAccountNumber = user1.ordinary_account_number;
        }
        else userAccountNumber = user1.savings_account_number;
        if(accountChoosed2==AccountChoosed.ORDINARYACCOUNT) receiverAccountNumber = receiver1.ordinary_account_number;
        else receiverAccountNumber = receiver1.savings_account_number;
        data.put("From account number: ",userAccountNumber);
        data.put("Paymaster: ",user1.firstName+" "+user1.lastName);
        data.put("To account number: ",receiverAccountNumber);
        data.put("Receiver: ",receiver1.firstName+" "+receiver1.lastName);
        data.put("Transfer amount: ",transferData.get("kwota")+" "+"PLN");
        data.put("Amount booked: ","-"+transferData.get("kwota")+" "+"PLN");
        data.put("Title: ",transferData.get("tytul"));
    }
}