package src.transfers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public interface PdfGenerator{
    public void generatePDF(String directory) throws IOException;
}

class PdfGeneratorStandard implements PdfGenerator {
    protected final String title = "Potwierdzenie wykonania operacji";
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

    protected float titleFontSize = 20;
    protected float dateFontSize = 14;
    protected float dataFontSize = 12;

    public PdfGeneratorStandard(){}
    public PdfGeneratorStandard(String operationDate, Map<String, String> senderData, Map<String, String> receiverData, Map<String,String> transferData){
        fileName = "Potwierdzenie_"+transferData.get("typ")+"_"+senderData.get("nazwa odbiorcy")+senderData.get("nazwa odbiorcy cd")+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Typ operacji: ",transferData.get("typ"));
        data.put("Data księgowania: ", operationDate);
        data.put("Data transakcji: ", operationDate);
        data.put("Z rachunku: ",senderData.get("nr konta"));
        String sender = "";
        sender+=senderData.get("nazwa odbiorcy");
        sender = sender+" "+senderData.get("nazwa odbiorcy cd");
        if(senderData.containsKey("miejscowosc")){
            sender = sender+" "+senderData.get("miejscowosc");
            sender = sender+" "+senderData.get("kod pocztowy");
            sender = sender+" "+"ul."+senderData.get("ulica");
            sender = sender+" "+senderData.get("nr domu");
        }
        data.put("Zleceniodawca: ",sender);
        data.put("Na rachunek: ",receiverData.get("nr konta"));
        String receiver = "";
        receiver+=receiverData.get("nazwa odbiorcy");
        receiver = receiver+" "+receiverData.get("nazwa odbiorcy cd");
        if(receiverData.containsKey("miejscowosc")){
            receiver = receiver+" "+receiverData.get("miejscowosc");
            receiver = receiver+" "+receiverData.get("kod pocztowy");
            receiver = receiver+" "+"ul."+receiverData.get("ulica");
            receiver = receiver+" "+receiverData.get("nr domu");
        }
        data.put("Odbiorca: ",receiver);
        data.put("Kwota transakcji: ",transferData.get("kwota")+" "+transferData.get("waluta"));
        data.put("Kwota zaksięgowana: ", "-" + String.format("%.2f",Double.parseDouble(transferData.get("kwotaPLN")))+" "+"PLN");
        data.put("Tytuł: ",transferData.get("tytul"));
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
        contentStream.showText("Data wygenerowania: ");
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
    public PdfGeneratorStandingOrder(String operationDate, Map<String, String> senderData, Map<String, String> receiverData, Map<String,String> transferData) {
        fileName = "Potwierdzenie_"+transferData.get("typ")+"_"+senderData.get("nazwa odbiorcy")+senderData.get("nazwa odbiorcy cd")+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Typ operacji: ",transferData.get("typ"));
        data.put("Data zlecenia: ", operationDate);
        data.put("Rozpoczęcie obowiązywania zlecenia: ", transferData.get("startdata"));
        if(transferData.containsKey("enddata"))  data.put("Zakończenie obowiązywania zlecenia: ", transferData.get("enddata"));
        data.put("Z rachunku: ",senderData.get("nr konta"));
        String sender = "";
        sender+=senderData.get("nazwa odbiorcy");
        sender = sender+" "+senderData.get("nazwa odbiorcy cd");
        if(senderData.containsKey("miejscowosc")){
            sender = sender+" "+senderData.get("miejscowosc");
            sender = sender+" "+senderData.get("kod pocztowy");
            sender = sender+" "+"ul."+senderData.get("ulica");
            sender = sender+" "+senderData.get("nr domu");
        }
        data.put("Zleceniodawca: ",sender);
        data.put("Na rachunek: ",receiverData.get("nr konta"));
        String receiver = "";
        receiver+=receiverData.get("nazwa odbiorcy");
        receiver = receiver+" "+receiverData.get("nazwa odbiorcy cd");
        if(receiverData.containsKey("miejscowosc")){
            receiver = receiver+" "+receiverData.get("miejscowosc");
            receiver = receiver+" "+receiverData.get("kod pocztowy");
            receiver = receiver+" "+"ul."+receiverData.get("ulica");
            receiver = receiver+" "+receiverData.get("nr domu");
        }
        data.put("Odbiorca: ",receiver);
        data.put("Kwota transakcji: ",transferData.get("kwota")+" "+transferData.get("waluta"));
        data.put("Kwota zaksięgowana: ", "-" + String.format("%.2f",Double.parseDouble(transferData.get("kwotaPLN")))+" "+"PLN");
        data.put("Tytuł: ",transferData.get("tytul"));
    }
}

class PdfGeneratorBLIK extends PdfGeneratorStandard implements PdfGenerator{
    public PdfGeneratorBLIK(String operationDate, Map<String, String> senderData, Map<String,String> receiverData, Map<String,String> transferData){
        fileName = "Potwierdzenie_"+"BLIK"+"_"+senderData.get("nazwa odbiorcy")+senderData.get("nazwa odbiorcy cd")+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Typ operacji: ",transferData.get("typ"));
        data.put("Data księgowania: ", operationDate);
        data.put("Data transakcji: ", operationDate);
        data.put("Z rachunku: ",senderData.get("nr konta"));
        data.put("Płacący: ",senderData.get("nazwa odbiorcy")+" "+senderData.get("nazwa odbiorcy cd"));
        data.put("Na nr. telefonu: ",receiverData.get("nr telefonu"));
        data.put("Odbiorca: ",receiverData.get("nazwa odbiorcy")+" "+receiverData.get("nazwa odbiorcy cd"));
        data.put("Kwota transakcji: ",transferData.get("kwota")+" "+"PLN");
        data.put("Kwota zaksięgowana: ","-"+transferData.get("kwota")+" "+"PLN");
        data.put("Tytuł: ",transferData.get("tytul"));
    }
}

class PdfGeneratorOwn extends PdfGeneratorStandard implements PdfGenerator{
    public PdfGeneratorOwn(String operationDate, Map<String, String> senderData, Map<String,String> receiverData, Map<String,String> transferData){
        fileName = "Potwierdzenie_"+transferData.get("typ")+"_"+senderData.get("nazwa odbiorcy")+senderData.get("nazwa odbiorcy cd")+"_"+generationDate+".pdf";
        data = new LinkedHashMap<>();
        data.put("Typ operacji: ",transferData.get("typ"));
        data.put("Data księgowania: ", operationDate);
        data.put("Data transakcji: ", operationDate);
        data.put("Z rachunku: ",senderData.get("nr konta"));
        data.put("Płacący: ",senderData.get("nazwa odbiorcy")+" "+senderData.get("nazwa odbiorcy cd"));
        data.put("Na rachunek: ",receiverData.get("nr konta"));
        data.put("Odbiorca: ",receiverData.get("nazwa odbiorcy")+" "+receiverData.get("nazwa odbiorcy cd"));
        data.put("Kwota transakcji: ",transferData.get("kwota")+" "+"PLN");
        data.put("Kwota zaksięgowana: ","-"+transferData.get("kwota")+" "+"PLN");
        data.put("Tytuł: ",transferData.get("tytul"));
    }
}