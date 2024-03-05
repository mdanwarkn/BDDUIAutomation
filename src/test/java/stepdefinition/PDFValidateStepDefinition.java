package stepdefinition;

import PDF.SEBIPDFPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PDFValidateStepDefinition {

    private static final String DOWNLOAD_FOLDER_PATH = System.getProperty("user.dir")+ File.separator+"OutputFile";
    private static final String SEBI_URL = "https://www.sebi.gov.in/enforcement/orders/jun-2019/adjudication-order-in-respect-of-three-entities-in-the-matter-of-prism-medico-and-pharmacy-ltd-_43323.html";
    WebDriver driver;
    SoftAssert softAssert;
    SEBIPDFPage pdf;
    @Before("@PDFTest")
    public void setUpDriver(){
        Map<String,Object> preferences= new HashMap<>();
        preferences.put("download.default_directory",DOWNLOAD_FOLDER_PATH);
        System.out.println("PATH "+DOWNLOAD_FOLDER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs",preferences);
        options.addArguments("start-maximized");
        driver =  new ChromeDriver(options);
        softAssert = new SoftAssert();
    }

    @After("@PDFTest")
    public void closeBrowser(){
        driver.quit();
        softAssert.assertAll();
    }

    @Given("The User opens the embedded PDF in SEBI application")
    public void the_user_opens_the_embedded_pdf_in_sebi_application() {
        driver.get(SEBI_URL);
        pdf = new SEBIPDFPage(driver , softAssert);
    }

    @Then("The User should see the PDF header as {string} {string}")
    public void the_user_should_see_the_pdf_header_as(String header1, String header2) {
        pdf.verifyPDFHeader(header1 , header2);
    }

    @When("The User clicks on pdf download button")
    public void the_user_clicks_on_pdf_download_button() throws InterruptedException {
        pdf.clickDownloadPDF();
    }
    @Then("The PDF should be downloaded")
    public void the_pdf_should_be_downloaded() {
        pdf.verifyPDFDownloaded(DOWNLOAD_FOLDER_PATH);
    }

    @And("Extract The content from the downloaded PDF")
    public void extract_the_content_from_the_downloaded_pdf(){
        pdf.extractPDFContent(DOWNLOAD_FOLDER_PATH);
    }

}
