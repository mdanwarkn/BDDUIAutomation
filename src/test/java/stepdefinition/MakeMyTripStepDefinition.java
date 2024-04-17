package stepdefinition;

import base.ui.tests.BaseStepDefinition;
import static base.ui.tests.EnvironmentManager.*;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import makemytrip.ui.pages.CompleteBookingPage;
import makemytrip.ui.pages.HomePage;
import makemytrip.ui.pages.PaymentsPage;
import makemytrip.ui.pages.SearchResultsPage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.asserts.SoftAssert;
import utilities.DriverUtil;
import utilities.ExcelUtil;
import static common.StaticClass.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class MakeMyTripStepDefinition extends BaseStepDefinition {

    HomePage homePage;
    SearchResultsPage searchResultsPage;
    CompleteBookingPage completeBookingPage;
    PaymentsPage paymentsPage;
    Object[][] objectArr = null;
    Map<String,String> inputRow = null;

    @Before("@MakeMyTripTest")
    public void setUpDriver(){
        driver =  new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT));
        driver.manage().window().maximize();
        softAssert = new SoftAssert();
    }

    @After("@MakeMyTripTest")
    public void closeBrowser(){
        driver.quit();
        softAssert.assertAll();
    }
    @Given("The User Opens Make My Trip Application")
    public void the_user_opens_make_my_trip_application() {
        driver.get(getTestEnvironmentURL("MakeMyTrip"));
        homePage = new HomePage(driver , softAssert);
    }
    @Given("Close if any advertisement banner is popping up")
    public void close_if_any_advertisement_banner_is_popping_up() {
        homePage.closeAdvertisementBanner();
    }

    @Given("The User has the flight booking details READ from the Excel {string}  Sheet {string} and the Details are {int}")
    public void the_user_has_the_flight_booking_details_read_from_the_excel_sheet_and_the_details_are(String workbook, String sheet , int rowNo) throws IOException, InvalidFormatException {
       if(objectArr == null ){
            objectArr = ExcelUtil.readDataFromExcelFile(INPUT_SHEET_PATH+workbook+".xlsx" , sheet);
        }
        inputRow = (Map<String,String>) objectArr[rowNo][0];
    }

    @When("The User enters the Source , Destination and Travel Date")
    public void the_user_enters_the_source_destination_and_travel_date() throws InterruptedException {
        homePage.enterTravelDetails(inputRow);
    }
    @When("Clicks on Search flight")
    public void clicks_on_search_flight() throws InterruptedException {
        homePage.clickSearchFlights();
        searchResultsPage = new SearchResultsPage(driver , softAssert);
    }
    @Then("User should be landed on Flight Search Results Screen")
    public void user_should_be_landed_on_flight_search_results_screen() {
        searchResultsPage.verifyTravelLocation(inputRow);
    }
    @Then("Should be shown with the Cheapest flight on the top")
    public void should_be_shown_with_the_cheapest_flight_on_the_top() {
        searchResultsPage.verifyFlightWithLowestFareIsAtTop();
    }

    @Then("User selects the Lowest Fare Flight")
    public void user_selects_the_lowest_fare_flight() {
        searchResultsPage.selectTheLowestFareFlight(inputRow);
    }

    @And("Select the Fare Option")
    public void select_the_fare_option() {
        searchResultsPage.selectFareOption(inputRow);
        completeBookingPage = new CompleteBookingPage(driver , softAssert);
    }

    @Then("User should be landed on Complete Booking Screen")
    public void user_should_be_landed_on_complete_booking_screen() {
        completeBookingPage.verifyCompleteBookingScreenHeader();
    }

    @Then("Check the flight booking details are populated correctly in Booking Screen")
    public void check_the_flight_booking_details_are_populated_correctly_in_booking_screen() {
        completeBookingPage.verifyBookingDetailsArePopulatedCorrectly(inputRow);
    }
    @Then("Add Travelled Details")
    public void add_travelled_details() {
        completeBookingPage.addTravellerDetails(inputRow);
    }
    @Then("Add Customer contact details")
    public void add_customer_contact_details() {
        completeBookingPage.enterContactDetails(inputRow);
    }
    @Then("Confirm the Billing details")
    public void confirm_the_billing_details() {
        completeBookingPage.selectConfirmBillingDetailsCheckbox();
    }
    @Then("Click {string} in confirm booking screen")
    public void click_in_confirm_booking_screen(String buttonName) {
        completeBookingPage.clickContinueButton(buttonName);
    }

    @And("Verify the passenger details in Review Modal and Click {string}")
    public void verify_the_passenger_details_in_Review_Modal_and_Click(String buttonName) {
        //Need code to verify passenger detail in review modal
        completeBookingPage.clickButtonUsingJS(buttonName);
    }

    @Then("User should be landed on Seat Booking section")
    public void user_should_be_landed_on_seat_booking_section() {
        completeBookingPage.verifySeatBookingSectionIsEnabled();
    }

    @Then("User Selects the seat for the passeneger included in the journey")
    public void user_selects_the_seat_for_the_passeneger_included_in_the_journey() {
        completeBookingPage.selectSeats(inputRow);
    }

    @Then("Selects Skip to add-ons after seat selection")
    public void selects_Skip_to_ad_ons_after_seat_selection(String option) {
       completeBookingPage.selectSkipToAddOns();
    }

    @Then("The Button {string} should be visible and when the user clicks on proceeds to pay")
    public void the_button_should_be_visible_and_when_the_user_proceeds_to_pay(String buttonName) {
        completeBookingPage.clickButtonUsingJS(buttonName);
    }

    @Then("{string} if airfare price increased during the process of flight booking")
    public void if_airfare_price_increased_during_the_process_of_flight_booking(String action) {
        completeBookingPage.reflectOnAirFarePriceIncreased(action);
        paymentsPage = new PaymentsPage(driver , softAssert);
    }

    @Then("User should be landed on Payment Screen")
    public void user_should_be_landed_on_payment_screen() {
        paymentsPage.verifyPaymentsScreenHeader();
    }


}
