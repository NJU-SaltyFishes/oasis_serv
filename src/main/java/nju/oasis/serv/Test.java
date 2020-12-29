package nju.oasis.serv;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.IOException;

public class Test {
    public static void main(String[]args){
        try {
            //System.out.println(getPdfFileText("/Users/Karl/Desktop/Teddy.pdf"));
            System.out.println(getPdfFileText("/Users/Karl/Desktop/3372224.3380880.pdf"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getPdfFileText(String filename) throws IOException{
        PdfReader reader = new PdfReader(filename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        StringBuffer buff = new StringBuffer();
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i,new SimpleTextExtractionStrategy());
            buff.append(strategy.getResultantText());
        }
        return buff.toString();
    }
}
