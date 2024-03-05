package stepdefinition;

import flipkart.ui.pages.HomePage;
import flipkart.ui.pages.SearchPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

public class FlipkartAppStepDefinition {

    private static final String FLIPKART_URL = "https://www.flipkart.com/";
    WebDriver driver;
    SoftAssert softAssert;
    HomePage homePage;
    SearchPage searchPage;

    @Before("@FlipkartTest")
    public void setUpDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver =  new ChromeDriver(options);
        softAssert = new SoftAssert();
    }

    @After("@FlipkartTest")
    public void closeBrowser(){
        driver.quit();
        softAssert.assertAll();
    }

    @Given("The User opens the flipkart application")
    public void the_user_opens_the_flipkart_application() {
        homePage = new HomePage(driver , softAssert);
        driver.get(FLIPKART_URL);
    }

    @Then("The User should see the search box in home screen")
    public void the_user_should_see_the_search_box_in_home_screen() {
        homePage.verifySearchBoxInHomeScreen();
    }

    @When("The User searches {string} in the search box")
    public void the_user_searches_in_the_search_box(String input) {
        homePage.enterValueAndClickSearch(input);
    }
    @Then("The User should see the item {string} in the search result")
    public void the_user_should_see_the_item_in_the_search_result(String item) {
        searchPage = new SearchPage(driver , softAssert);
        searchPage.verifyTheSearchResults(item);
    }
    @Then("The compareTo option should be present and enabled for that item")
    public void the_compare_to_option_should_be_present_and_enabled_for_that_item() {
        searchPage.verifyAddToCompareOptionIsEnabled();
    }
}
