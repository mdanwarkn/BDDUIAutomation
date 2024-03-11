package flipkart.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class SearchPage extends BasePage {

    protected static final String ADD_TO_COMPARE = "Add to Compare";
    protected static final String ADD_TO_COMPARE_OPTION = DIV_TEXT + "/preceding::*[text()='%s'][1]/preceding::input[@type='checkbox'][1]";

    String item;

    public SearchPage(WebDriver driver , SoftAssert softAssert){
        super(driver, softAssert);
    }

    public void verifyTheSearchResults(String item){
        this.item = item;
        scrollToElement(getLocator(DIV_TEXT , item) , 30);
        verifyElementIsVisible(getLocator(DIV_TEXT , item) , 10);
    }

    public void verifyAddToCompareOptionIsEnabled(){
        verifyElementIsEnabled(getLocator(ADD_TO_COMPARE_OPTION , item , ADD_TO_COMPARE) , 30);
    }

}
