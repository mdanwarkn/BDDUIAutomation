package makemytrip.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import utilities.Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.StaticClass.*;

public class SearchResultsPage extends BasePage {

    private static final String PRICE_CLASS_NAME = "ViewPrice";
    private static final String SEARCH_CLASS_NAME = "listingCard  appendBottom5";

   /* private static final String PARTIAL_DEPARTURE_TIME_CLASS_NAME = "timeInfoLeft";

    private static final String PARTIAL_STOP_INFO_CLASS_NAME = "stop-info";

    private static final String LAYOVER_CLASS_NAME = "flightsLayoverInfo";

    private static final String PARTIAL_ARRIVAL_TIME_CLASS_NAME = "timeInfoRight";*/


    public SearchResultsPage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
    }

    public void handleFareLockModalPopUp(){
        if(elementExistsNoReporting(getLocator(ELEMENT_WITH_CLASS , "fareLockInfoContainer") , MEDIUM_TIMEOUT)) {
            clickTheElementWhenVisible(getLocator(BUTTON_WITH_TEXT, "OKAY, GOT IT!"), SHORT_TIMEOUT);
        }
    }

    public void verifyTravelLocation(Map<String,String> currentRow){
        handleFareLockModalPopUp();
        scrollToBottom();
        scrollToTop();
        String expectedText = "Flights from " + currentRow.get("From") + " to "+ currentRow.get("To");
        String actualText = geTextWhenVisible(getLocator(CONTAINS_TEXT , "Flights from") , MEDIUM_TIMEOUT);
        verifyText(actualText , expectedText);
    }

    public void verifyFlightWithLowestFareIsAtTop(){
        List<WebElement>  searchResults = getWebElements(getLocator(ELEMENT_CONTAINS_CLASS, PRICE_CLASS_NAME) , MEDIUM_TIMEOUT);
        List<Integer> prices = new ArrayList<>();
        for(WebElement searchResult : searchResults){
            prices.add(Integer.parseInt(searchResult.getText().replaceAll("[^\\d+]","")));
        }
        softAssert.assertTrue(Utility.IsFirstElementInTheListIsLowest(prices),"First element in the list is not lowest");
    }

   /* public void findTheFlightStoppages(Map<String,String> currentRow){
        if(elementExistsNoReporting(getLocator(ELEMENT_WITH_CLASS , SEARCH_CLASS_NAME))){

        }
    }*/

    public void selectTheLowestFareFlight(Map<String,String> currentRow) {
        scrollToElement(getLocator(ELEMENT_WITH_TEXT_INDEX , "BOOK NOW" ,"1") , MEDIUM_TIMEOUT);
        clickTheElementWhenVisibleUsingJS(getLocator(ELEMENT_WITH_TEXT_INDEX , "BOOK NOW" ,"1") , SHORT_TIMEOUT);
       // findTheFlightStoppages(currentRow);
    }

    public void selectFareOption(Map<String,String> currentRow){
        clickTheElementWhenVisible(getLocator(BUTTON_WITH_TEXT,  "BOOK NOW") , MEDIUM_TIMEOUT);
        waitUntilNoOfTabsIsN(driver , 2 , 20);
    }

}