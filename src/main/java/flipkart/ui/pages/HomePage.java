package flipkart.ui.pages;

import base.BasePage;
import base.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class HomePage extends BasePage {


    private static final String SEARCH_BOX_TEXT = "Search for Products, Brands and More";

    public HomePage(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
    }

    public void verifySearchBoxInHomeScreen(){
        verifyElementIsVisible(getLocator(INPUT_WITH_TITLE , SEARCH_BOX_TEXT),30);
        verifyAttributeWhenVisible(getLocator(INPUT_WITH_TITLE , SEARCH_BOX_TEXT),"placeholder",SEARCH_BOX_TEXT,30);
    }

    public void enterValueAndClickSearch(String input){
        enterValueWhenVisible(getLocator(INPUT_WITH_TITLE , SEARCH_BOX_TEXT),input , 30);
        clickTheElementWhenVisible(getLocator(BUTTON_WITH_TITLE , SEARCH_BOX_TEXT),30);
    }

}
