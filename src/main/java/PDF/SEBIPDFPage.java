package PDF;

import base.BasePage;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SEBIPDFPage extends BasePage {
    protected static final String PDF_HEADER = "//div[@class='pdfViewer']/*[1]//*[text()='%s']";

    private static final String ORDER_PDF_TITLE  = "Adjudication Order in respect of three entities in the matter of Prism Medico and Pharmacy Ltd.";

    private static final String ORDER_PDF_NAME = "1560873687935";

    public SEBIPDFPage(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
    }


    public void verifyPDFHeader(String header1 , String header2){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(getWebElement(getLocator(IFRAME_WITH_TITLE, ORDER_PDF_TITLE) , 30));
        verifyElementIsVisible(
                RelativeLocator.with(getLocator(PDF_HEADER , header1))
                        .above(getLocator(PDF_HEADER , header2))
                , 30
        );
    }

    public void clickDownloadPDF() throws InterruptedException {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(getWebElement(getLocator(IFRAME_WITH_TITLE, ORDER_PDF_TITLE) , 30));
        clickTheElementWhenVisible(getLocator(ELEMENT_WITH_CLASS + BUTTON_WITH_TITLE , "toolbar","Download") , 30);

        Thread.sleep(5000);
    }

    public void verifyPDFDownloaded(String path){
        File file = new File( path + File.separator + ORDER_PDF_NAME+".pdf");
        if(!file.exists()){
            softAssert.assertTrue(false , "PDF file not downloaded");
        }
    }

    public void extractPDFContent(String path){
        BodyContentHandler handler =  new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        PDFParser pdfParser = new PDFParser();
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(new File( path + File.separator + ORDER_PDF_NAME+".pdf"));
            pdfParser.parse(fileInputStream , handler , metadata , parseContext);
            String pdfContent = handler.toString();
            System.out.println("************ PDF Content **********\n"+pdfContent);
        }catch(IOException | TikaException | SAXException e){
            softAssert.assertTrue(false , e.getMessage());
        }
    }
}
