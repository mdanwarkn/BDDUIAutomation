package common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import utilities.DriverUtil;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

import static com.aventstack.extentreports.Status.*;
import static common.StaticClass.MEDIUM_TIMEOUT;

public class BasePage {

    //CONSTANTS

    private static final String ELEMENT_VISIBLE = "Element - '%s' is visible in '%d' seconds";

    private static final String ELEMENT_NOT_VISIBLE = "Element - '%s' is not visible after waited for '%d' seconds \nException : %s";

    private static final String EXPECTED_TEXT_WITH_LOCATION_MATCHING = "Text - '%s' is present in the location '%s' in '%d' seconds";

    private static final String EXPECTED_TEXT_WITH_LOCATION_NOT_MATCHING = "Text - '%s' is not present in the location '%s' and waited for '%d' seconds <br><br> Actual Text found is `: '%s'";

    private static final String EXPECTED_TEXT_MATCHING = "Expected Text : '%s' found";

    private static final String EXPECTED_TEXT_NOT_MATCHING = "Expected Text '%s' not found \nActual Text found : '%s'";

    private static final String EXPECTED_ATTRIBUTE_VALUE_WITH_LOCATION_MATCHING = "Attribute - '%s' with value - '%s' is present in the location  '%s' in '%d' seconds";

    private static final String EXPECTED_ATTRIBUTE_VALUE_WITH_LOCATION_NOT_MATCHING = "Attribute - '%s' with value - '%s' is not present in the location '%s' in '%d' seconds\nActual value found : %s";

    private static final String CLICKING_THE_ELEMENT = "Clicking the element present in the location - '%s' in '%d' seconds";

    private static final String SENDING_CLEAR = "Clearing the content in the text box - '%s'";
    private static final String ENTERING_TEXT = "Entering the text - '%s' in the text box - '%s'";

    //Locators
    protected static final String ANCESTOR_CONTAINS_CLASS = "/ancestor::*[contains(@class,'%s')]";
    protected static final String ELEMENT_WITH_TEXT_INDEX = "(//*[text()='%s'])[%s]";

    protected static final String IFRAME_CONTAINS_TITLE = "//iframe[contains(@title,'%s')]";
    protected static final String LIST_CONTAINS_STRING = "//li[contains(string() , '%s')]";

    protected static final String INPUT_WITH_TITLE = "//input[@title='%s']";

    protected static final String ELEMENT_WITH_ARIALABEL = "//*[@aria-label='%s']";

    protected static final String INPUT_WITH_PLACEHOLDER = "//input[@placeholder='%s']";

    protected static final String INPUT_TAG = "//input";

    protected static final String BUTTON_WITH_TITLE = "//button[@title='%s']";

    protected static final String BUTTON_WITH_TEXT = "//button[text()='%s']";

    protected static final String DIV_TEXT = "//div[text()='%s']";

    protected static final String ELEMENT_WITH_CLASS = "//*[@class='%s']";

    protected static final String ELEMENT_WITH_CLASS_WITH_INDEX = "(//*[@class='%s'])[%s]";

    protected static final String ELEMENT_WITH_PLACEHOLDER = "//*[@placeholder='%s']";

    protected static final String IFRAME_WITH_TITLE = "//iframe[@title='f%s']";

    protected static final String ELEMENT_CONTAINS_CLASS = "//*[contains(@class,'%s')]";
    protected static final String ELEMENT_CONTAINS_CLASS_WITH_INDEX = ELEMENT_CONTAINS_CLASS + "[%s]";

    protected static final String ELEMENT_CONTAINS_ARIALABEL = "//*[contains(@aria-label,'%s')]";

    protected static final String SELECT_WITH_ARIALABEL = "//select[@aria-label='%s']";

    protected static final String H3 = "//h3";
    protected static final String H2_STRING = "//h2[string()='%s']";

    protected static final String ELEMENT_WITH_STRING = "//*[string()='%s']";

    protected static final String TEXT = "//*[text()='%s']";

    protected static final String TEXT_SINGLE_INVERTED = "//*[text()=\"%s\"]";

    protected static final String CONTAINS_TEXT = "//*[contains(text() , '%s')]";

    protected static final String FOLLOWING_INPUTBOX = TEXT + "/following::input[1]";

    protected static final String EM_WITH_INDEX = "(//em)[%s]";

    protected static final String FOLLOWING_SIBLING = "//*[normalize-space(text())='%s']/following-sibling::*";

    protected static final String ANY_FOLLOWING_SIBLING = "/following-sibling::*";

    protected static final String LABEL_TEXT = "//label[text()='%s']";
    protected static final String SPAN = "//span";

    protected WebDriver driver;
    protected SoftAssert softAssert;
    protected DriverUtil driverUtil;

    protected BasePage(WebDriver driver , SoftAssert softAssert){
        this.driver =  driver;
        this.softAssert = softAssert;
        this.driverUtil = new DriverUtil(driver);
    }

    public static synchronized By getLocator(String locator) {
        return By.xpath(locator);
    }

    public static synchronized By getLocator(String locator , String value1) {
        return By.xpath(String.format(locator, value1));
    }

    public static synchronized By getLocator(String locator , String value1 , String value2) {
        return By.xpath(String.format(locator, value1 , value2));
    }

    public void verifyElementIsVisible(By locator , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logPassAndTakeScreenshot(String.format(ELEMENT_VISIBLE,  locator , timeOut));
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            logFailAndTakeScreenShot(String.format(ELEMENT_NOT_VISIBLE ,  locator , timeOut , e.getMessage()));
        }
    }

    public String verifyTextWhenVisible(By locator , String expectedText , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualText = driver.findElement(locator).getText();
            actualText = formatNullToEmpty(actualText);
            if(actualText.equals(expectedText)){
                logPassAndTakeScreenshot(String.format(EXPECTED_TEXT_WITH_LOCATION_MATCHING ,expectedText , locator , timeOut));
            }else{
                logFailAndTakeScreenShot(String.format(EXPECTED_TEXT_WITH_LOCATION_NOT_MATCHING ,expectedText , locator , timeOut ,  actualText));
            }
            return actualText;
        }catch(StaleElementReferenceException | NoSuchElementException e) {
            logFailAndTakeScreenShot(String.format(ELEMENT_NOT_VISIBLE , locator , timeOut ,  e.getMessage()));
            return null;
        }
    }

    public void verifyText(String actualText , String expectedText) {
        actualText = formatNullToEmpty(actualText);
        if(actualText.equals(expectedText)){
            logPassAndTakeScreenshot(String.format(EXPECTED_TEXT_MATCHING , expectedText));
        }else{
            logFailAndTakeScreenShot(String.format(EXPECTED_TEXT_NOT_MATCHING ,expectedText , actualText));
        }
    }

    public String formatNullToEmpty(String str){
        return str==null?"":str;
    }

    public void verifyAttributeWhenVisible(By locator , String attribute , String expectedValue , int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualValue = driver.findElement(locator).getAttribute(attribute);
            actualValue = formatNullToEmpty(actualValue);
            if(actualValue.equals(expectedValue)){
                logPassAndTakeScreenshot(String.format(EXPECTED_ATTRIBUTE_VALUE_WITH_LOCATION_MATCHING ,attribute , expectedValue , locator , timeOut));
            }else{
                logFailAndTakeScreenShot(String.format(EXPECTED_ATTRIBUTE_VALUE_WITH_LOCATION_NOT_MATCHING , attribute , expectedValue , locator , timeOut , actualValue));
            }
        }catch(StaleElementReferenceException | NoSuchElementException e) {
            logFailAndTakeScreenShot(String.format(ELEMENT_NOT_VISIBLE ,  locator , timeOut , e.getMessage()));
        }
    }

    public void clearAndEnterValueWhenVisible(By locator , String text , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            logInfo(String.format(SENDING_CLEAR , locator));
            element.clear();
            logInfo(String.format(ENTERING_TEXT , text , locator));
            element.sendKeys(text);
        }catch(Exception e) {
            logFailAndTakeScreenShot(e.getMessage());
        }
    }

    public void clearAndEnterTextWhenVisibleUsingJS(By locator , String text , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].innerText=arguments[1];",element,text);
        }catch(Exception e) {
            logFailAndTakeScreenShot(e.getMessage());
        }
    }

    public void clickTheElementWhenVisible(By locator , int timeOut){
        try {
            logInfo(String.format(CLICKING_THE_ELEMENT , locator , timeOut));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            driver.findElement(locator).click();
        }catch(StaleElementReferenceException | NoSuchElementException | TimeoutException e) {
            logFailAndTakeScreenShot(e.getMessage());
        }
    }

    public void clickTheElementWhenVisibleUsingJS(By locator , int timeOut){
        try {
            logInfo(String.format(CLICKING_THE_ELEMENT , locator , timeOut));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].focus();",element);
            javascriptExecutor.executeScript("arguments[0].click();",element);
        }catch(StaleElementReferenceException | NoSuchElementException | TimeoutException e) {
            logFailAndTakeScreenShot(e.getMessage());
        }
    }

    public void clickTheElementWhenExistsUsingJS(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].focus();",element);
            javascriptExecutor.executeScript("arguments[0].click();",element);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void scrollToElement(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("arguments[0].scrollIntoView();",element);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void scrollToBottom(){
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void scrollToTop(){
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("window.scrollTo(0 , 0)");
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public boolean verifyElementIsEnabled(By locator , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
             if(element.isEnabled())
                 return true;
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
        return false;
    }

    public  WebElement getWebElement(By locator , int timeOut){
        try {
            FluentWait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NotFoundException.class);

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public List<WebElement> getWebElements(By locator , int timeOut){
        try {
            FluentWait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NotFoundException.class);

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElements(locator);
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public  void selectByVisibleText(By locator , String option , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement element =  driver.findElement(locator);
            Select select = new Select(element);
            select.selectByVisibleText(option);
        }catch(Exception e) {softAssert.assertTrue(false , e.getMessage());
        }
    }


    public  String geTextWhenVisible(By locator ,  int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).getText();
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
            return null;
        }
    }

    public  boolean elementExistsNoReporting(By locator ,  int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public void clickButtonUsingJS(String buttonName){
        clickTheElementWhenVisibleUsingJS(getLocator(BUTTON_WITH_TEXT ,  buttonName) , MEDIUM_TIMEOUT);
        System.out.println("Check");
    }

    public void waitUntilNoOfTabsIsN(WebDriver driver , int N , int timeOut){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(ExpectedConditions.numberOfWindowsToBe(N));
        }catch(Exception e) {
            softAssert.assertTrue(false , e.getMessage());
        }
    }

    public void logFailAndTakeScreenShot(String message){
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        testStep.log(FAIL, MarkupHelper.createLabel(message , ExtentColor.RED) , MediaEntityBuilder.createScreenCaptureFromPath(driverUtil.takeScreenShotAsFile(), "Screenshot local file").build());
        softAssert.assertTrue(false , message);
    }

    public void takeScreenShotInBase64(String message){
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        testStep.log(INFO, MarkupHelper.createLabel(message , ExtentColor.BLACK) , MediaEntityBuilder.createScreenCaptureFromBase64String(driverUtil.takeScreenShotInBase64() , "Screenshot Base 64").build());
    }

    public void takeScreenShot(String message){
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        testStep.log(INFO, MarkupHelper.createLabel(message , ExtentColor.GREY) , MediaEntityBuilder.createScreenCaptureFromPath(driverUtil.takeScreenShotAsFile()).build());
    }

    public void logPassAndTakeScreenshot(String message) {
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        testStep.log(PASS, MarkupHelper.createLabel(message , ExtentColor.GREEN));
    }

    public void logInfo(String message){
        ExtentTest testStep = ExtentCucumberAdapter.getCurrentStep();
        testStep.log(INFO , message);
    }
}
