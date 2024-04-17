package base.ui.tests;

import bob.ui.pages.FixedDepositCalculator;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.time.Duration;

public class BaseStepDefinition {
    protected static final String DOWNLOAD_FOLDER_PATH = System.getProperty("user.dir")+ File.separator+"OutputFile";
    protected static final String OUTPUT_WORKBOOK_NAME = "Output_Results.xlsx";
    protected WebDriver driver;
    protected SoftAssert softAssert;


}
