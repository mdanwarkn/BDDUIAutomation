package makemytrip.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import static common.StaticClass.*;

public class CompleteBookingPage extends BasePage {

    private static final String TRAVELLER_DETAIL = ELEMENT_WITH_STRING + ANY_FOLLOWING_SIBLING;
    private static final String AVAILABLE_SEAT_CLASS = "seatBlock pointer";

    private static final String BOOKING_DETAILS_SENT = "Booking details will be sent to";
    private static final String CONFIRM_BILLING_DETAILS = "Confirm and save billing details to your profile";
    private static final String GREAT_VALUE_ALERT_MSG = "Here’s a great-value seat that you may prefer!";
    private static final String SKIP_TO_ADD_ONS = "Skip to add-ons";
    private static final String CONTINUE_TO_PAY = "CONTINUE TO PAY";

    public CompleteBookingPage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
    }

    public void verifyCompleteBookingScreenHeader(){
        Set<String> windowId = driver.getWindowHandles();
        Iterator<String> iterator = windowId.iterator();
        String parentWindowId =  iterator.next();
        String childWindowsId = iterator.next();
        driver.switchTo().window(childWindowsId);
        verifyElementIsVisible(getLocator(H2_STRING , "Complete your booking") , MEDIUM_TIMEOUT);
    }

    public void verifyBookingDetailsArePopulatedCorrectly(Map<String, String> currentRow){
        verifyElementIsVisible(getLocator(H2_STRING , currentRow.get("From") + " → " + currentRow.get("To")) , SHORT_TIMEOUT);
    }

    public void addTravellerDetails(Map<String, String> currentRow){
        scrollToElement(getLocator(H2_STRING , "Traveller Details") , MEDIUM_TIMEOUT);
        int noOfTraveller = Integer.parseInt(currentRow.get("NoOfTraveller"));
        int i = 1;
        while(i<=noOfTraveller){
            clickTheElementWhenVisible(getLocator(BUTTON_WITH_TEXT , "+ ADD NEW ADULT") , SHORT_TIMEOUT);
            String passengerNo = "ADULT  "+String.valueOf(i);
            String firstMiddleName = currentRow.get("Traveller"+i+".FirstMiddleName");
            String lastName = currentRow.get("Traveller"+i+".LastName");
            String gender = currentRow.get("Traveller"+i+".Gender");
            String country = currentRow.get("Traveller"+i+".Country");

            clearAndEnterValueWhenVisible(getLocator(TRAVELLER_DETAIL + INPUT_WITH_PLACEHOLDER ,passengerNo, "First & Middle Name") , firstMiddleName , SHORT_TIMEOUT);
            clearAndEnterValueWhenVisible(getLocator(TRAVELLER_DETAIL + INPUT_WITH_PLACEHOLDER ,passengerNo, "Last Name") , lastName , SHORT_TIMEOUT);
            clickTheElementWhenVisibleUsingJS(getLocator(TRAVELLER_DETAIL + TEXT , passengerNo, gender) , SHORT_TIMEOUT);
            String countryElement = String.format(TRAVELLER_DETAIL,passengerNo) + String.format(LABEL_TEXT , "Country Code") + ANY_FOLLOWING_SIBLING + INPUT_TAG;
            clearAndEnterValueWhenVisible(getLocator(countryElement) ,country , MEDIUM_TIMEOUT);
            //System.out.println("Outer*** "+driver.findElement(getLocator(TRAVELLER_DETAIL , passengerNo)).getText());
            clickTheElementWhenExistsUsingJS(getLocator(TRAVELLER_DETAIL + CONTAINS_TEXT , passengerNo , country) ,MEDIUM_TIMEOUT);
            i++;
        }
    }

    public void enterContactDetails(Map<String,String> currentRow){
        String countryElement = String.format(TRAVELLER_DETAIL,BOOKING_DETAILS_SENT) + String.format(LABEL_TEXT , "Country Code") + ANY_FOLLOWING_SIBLING + INPUT_TAG;
        clearAndEnterValueWhenVisible(getLocator(countryElement) ,currentRow.get("CountryCode") , SHORT_TIMEOUT);
        clickTheElementWhenExistsUsingJS(getLocator(TRAVELLER_DETAIL + CONTAINS_TEXT , BOOKING_DETAILS_SENT , currentRow.get("CountryCode")) ,SHORT_TIMEOUT);
        clearAndEnterValueWhenVisible(getLocator(TRAVELLER_DETAIL + INPUT_WITH_PLACEHOLDER ,BOOKING_DETAILS_SENT, "Mobile No") , currentRow.get("MobileNo") , SHORT_TIMEOUT);
        clearAndEnterValueWhenVisible(getLocator(TRAVELLER_DETAIL + INPUT_WITH_PLACEHOLDER ,BOOKING_DETAILS_SENT, "Email") , currentRow.get("Email") , SHORT_TIMEOUT);
    }

    public void selectConfirmBillingDetailsCheckbox(){
        clickTheElementWhenVisibleUsingJS(getLocator(TEXT , CONFIRM_BILLING_DETAILS) , MEDIUM_TIMEOUT);
    }

    public void clickContinueButton(String buttonName){
        scrollToElement(getLocator(BUTTON_WITH_TEXT , buttonName) , MEDIUM_TIMEOUT);
        clickTheElementWhenVisibleUsingJS(getLocator(BUTTON_WITH_TEXT , buttonName) , MEDIUM_TIMEOUT);
    }

    public void verifySeatBookingSectionIsEnabled(){
        verifyElementIsVisible(getLocator(TEXT , "Seats") , MEDIUM_TIMEOUT);
    }

    public void closeGreatValueSeatAlert(){
        if(elementExistsNoReporting(getLocator(TEXT_SINGLE_INVERTED , GREAT_VALUE_ALERT_MSG),SHORT_TIMEOUT)){
            clickTheElementWhenExistsUsingJS(getLocator(TEXT , "Skip Seat Selection") , SHORT_TIMEOUT);
        }
    }

    public void selectSeats(Map<String,String> currentRow){
        int noOfTraveller = Integer.parseInt(currentRow.get("NoOfTraveller"));
        closeGreatValueSeatAlert();
        int i = 1;
        while(i++<=noOfTraveller){
            clickTheElementWhenVisibleUsingJS(getLocator(ELEMENT_WITH_CLASS_WITH_INDEX , AVAILABLE_SEAT_CLASS , String.valueOf(i)) , MEDIUM_TIMEOUT);
        }
    }

    public void selectSkipToAddOns(){
        scrollToElement(getLocator(TEXT , SKIP_TO_ADD_ONS) , MEDIUM_TIMEOUT);
        clickTheElementWhenVisible(getLocator(TEXT , SKIP_TO_ADD_ONS) , SHORT_TIMEOUT);
    }

    public void reflectOnAirFarePriceIncreased(String action){
        if(action.equals("Accept")){
            if(elementExistsNoReporting(getLocator(TEXT , CONTINUE_TO_PAY) , SHORT_TIMEOUT)){
                clickTheElementWhenVisible(getLocator(TEXT , CONTINUE_TO_PAY) , SHORT_TIMEOUT);
            }
        }
    }




}
