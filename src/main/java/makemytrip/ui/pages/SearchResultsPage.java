package makemytrip.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import utilities.Utility;

import java.util.ArrayList;
import java.util.List;

import static common.StaticClass.*;

public class SearchResultsPage extends BasePage {

    private static final String PRICE_CLASS_NAME = "ViewPrice";

    public SearchResultsPage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
    }


    public void verifyTravelLocation(){
        //Flights from
        
    }

    public void verifyFlightWithLowestFareIsAtTop(){
        List<WebElement>  searchResults = getWebElements(getLocator(ELEMENT_CONTAINS_CLASS, PRICE_CLASS_NAME) , MEDIUM_TIMEOUT);
        List<Integer> prices = new ArrayList<>();
        for(WebElement searchResult : searchResults){
            prices.add(Integer.parseInt(searchResult.getText().replaceAll("[^\\d+]","")));
        }
        softAssert.assertTrue(Utility.IsFirstElementInTheListIsLowest(prices),"First element in the list is not lowest");
    }

    public void selectTheLowestFareFlight(){
        clickTheElementWhenVisible(getLocator(BUTTON_WITH_INDEX , "BOOK NOW" ,"1") , MEDIUM_TIMEOUT);
    }


}