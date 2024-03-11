package stepdefinition;

import base.ui.tests.BaseStepDefinition;
import static base.ui.tests.EnvironmentManager.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import makemytrip.ui.pages.HomePage;
import makemytrip.ui.pages.SearchResultsPage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import utilities.ExcelUtil;
import static common.StaticClass.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class MakeMyTripStepDefinition extends BaseStepDefinition {

    HomePage homePage;
    SearchResultsPage searchResultsPage;

    Object[][] objectArr = null;
    Map<String,String> inputRow = null;

    @Before()
    public void setUpDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver =  new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT));
        softAssert = new SoftAssert();
    }

    @After()
    public void closeBrowser(){
        driver.quit();
        softAssert.assertAll();
    }
    @Given("The User Opens Make My Trip Application")
    public void the_user_opens_make_my_trip_application() {
        driver.navigate().to(getTestEnvironmentURL("MakeMyTrip"));
        homePage = new HomePage(driver , softAssert);
    }
    @Given("Close if any advertisement banner is popping up")
    public void close_if_any_advertisement_banner_is_popping_up() {
        homePage.closeAdvertisementBanner();
    }


    @Given("The User has the flight booking details READ from the Excel {string}  Sheet {string} and the Details are {int}")
    public void the_user_has_the_flight_booking_details_read_from_the_excel_sheet_and_the_details_are(String workbook, String sheet , int rowNo) throws IOException, InvalidFormatException {
       if(objectArr == null ){
            objectArr = ExcelUtil.readDataFromExcelFile(INPUT_SHEET_PATH+workbook , sheet);
        }
        inputRow = (Map<String,String>) objectArr[rowNo][0];
    }

    @When("The User enters the Source , Destination and Travel Date")
    public void the_user_enters_the_source_destination_and_travel_date() {
        homePage.enterTravelDetails(inputRow);
    }
    @When("Clicks on Search flight")
    public void clicks_on_search_flight() {
        homePage.clickSearchFlights();
        searchResultsPage = new SearchResultsPage(driver , softAssert);
    }
    @Then("User should be landed on Flight Search Results Screen And ")
    public void user_should_be_landed_on_flight_search_results_screen() {

    }
    @Then("Should be shown with the Cheapest flight on the top")
    public void should_be_shown_with_the_cheapest_flight_on_the_top() {
        searchResultsPage.verifyFlightWithLowestFareIsAtTop();
    }

}
