package stepdefinition;

import base.ui.tests.BaseStepDefinition;
import bob.ui.pages.FixedDepositCalculator;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import utilities.DriverUtil;
import utilities.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class BOBStepDefinition extends BaseStepDefinition {

    protected static final String BOB_FIXED_DEPOSIT_URL = "https://www.bankofbaroda.in/calculators/fixed-deposit-calculator";
    private static final String OUTPUT_SHEET_NAME = "BOB";

    private FixedDepositCalculator fixedDepositCalculator;

    @Before("@BOBTest")
    public void setUpDriver(Scenario s){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver =  new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        softAssert = new SoftAssert();
    }

    @After("@BOBTest")
    public void closeBrowser(){
        System.out.println("****** Driver closing");
        driver.quit();
        softAssert.assertAll();
    }

    @Given("The User is in BOB Fixed Deposit Calculator screen")
    public void the_user_is_in_bob_fixed_deposit_calculator_screen() {
        driver.navigate().to(BOB_FIXED_DEPOSIT_URL);
        fixedDepositCalculator = new FixedDepositCalculator(driver , softAssert);
    }

    @When("The User selects the Fixed Deposit Type as {string}")
    public void the_user_selects_the_fixed_deposit_type_as(String depositType) {
        fixedDepositCalculator.selectFixedDepositType(depositType);
    }

    @When("The User enters the Amount of fixed deposit as {string}")
    public void the_user_enters_the_amount_of_fixed_deposit_as(String amount) {
        fixedDepositCalculator.enterFixedDepositAmount(amount);
    }
    @When("The User enters the Tenure date as {string}")
    public void the_user_enters_the_tenure_date_as(String tenure) {
        fixedDepositCalculator.enterTenure(tenure);
    }
    @Then("Validate Rate of Interest is {string}")
    public void validate_rate_of_interest_is(String interest) {
        fixedDepositCalculator.validateRateOfInterest(interest);
    }
    @Then("Validate Maturity Date And Maturity Value")
    public void validate_maturity_date_and_maturity_value() {
        fixedDepositCalculator.validateMaturityDateAndValue();
    }
    @And("Store the FD results in output file")
    public void storeTheFDResultsInOutputFile() throws IOException, InvalidFormatException {
        String fileSource =  DOWNLOAD_FOLDER_PATH + File.separator + OUTPUT_WORKBOOK_NAME;
        ExcelUtil.writeDataToExcelFile(fixedDepositCalculator.currentRow, fileSource , OUTPUT_SHEET_NAME);
    }
  /*  @AfterStep
    public void captureScreenshot(){
        fixedDepositCalculator.takeScreenShot("Step Completed");
        *//*DriverUtil driverUtil = new DriverUtil(driver);
        s.attach(driverUtil.takeScreenShotInBytes() , "image/png" , "Screenshot after each step");*//*
    }*/

}
