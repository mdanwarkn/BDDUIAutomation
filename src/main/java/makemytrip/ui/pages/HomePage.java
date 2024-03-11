package makemytrip.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import static common.StaticClass.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
    }

    public void closeAdvertisementBanner(){
        clickTheElementWhenVisible(getLocator(ELEMENT_WITH_CLASS , "Close") , MEDIUM_TIMEOUT);
    }

    public void enterTravelLocation(String area , String location){
        clickTheElementWhenVisible(getLocator(TEXT , area) , MEDIUM_TIMEOUT);
        enterValueWhenVisible(getLocator(INPUT_WITH_PLACEHOLDER , area) , location , MEDIUM_TIMEOUT);
        clickTheElementWhenVisible(getLocator(LIST_CONTAINS_STRING , location) , MEDIUM_TIMEOUT);
    }

    public void selectTravelDate(String date){
        LocalDate localDate =  LocalDate.parse(date , DATE_TIME_FORMATTER_FORMAT1);
        String selectDate = localDate.format(DATE_TIME_FORMATTER_FORMAT2);
        String day = date.split(",")[0].trim();
        String monthYear = date.split(",")[1].trim();
        boolean dateFound = false;
        while(!dateFound){
            List<WebElement> list =  getWebElements(getLocator(ELEMENT_WITH_CLASS , "DayPicker-Caption") , MEDIUM_TIMEOUT);
            for(WebElement element : list){
                if(element.getText().equals(monthYear)){
                    dateFound = true;
                }
            }
            if(elementExistsNoReporting(getLocator(ELEMENT_WITH_ARIALABEL , "Next Month") , SHORT_TIMEOUT)){
                clickTheElementWhenVisible(getLocator(ELEMENT_WITH_ARIALABEL , "Next Month") , SHORT_TIMEOUT);
            }else{
                break;
            }
        }

        if(dateFound){
            clickTheElementWhenVisible(getLocator(ELEMENT_CONTAINS_ARIALABEL , selectDate) , SHORT_TIMEOUT);
        }else{
            //Date not found need to log in report
        }
    }

    public void enterTravelDetails(Map<String,String> currentRow){
        enterTravelLocation("From" , currentRow.get("From"));
        enterTravelLocation("To" , currentRow.get("To"));
        selectTravelDate(currentRow.get("DepartureDate"));
    }

    public void clickSearchFlights(){
        clickTheElementWhenVisible(getLocator(TEXT ,"Search") , MEDIUM_TIMEOUT);
    }






}
