package makemytrip.ui.pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import static common.StaticClass.*;

public class PaymentsPage extends BasePage {

    public PaymentsPage(WebDriver driver, SoftAssert softAssert) {
        super(driver, softAssert);
    }

    public void verifyPaymentsScreenHeader(){
        verifyElementIsVisible(getLocator(H2_STRING , "Payment options") , MEDIUM_TIMEOUT);
    }
}