package stepdefinition;

import IMDB.ui.pages.AdvancedTileSearchPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;
import utilities.ExcelUtil;

import java.io.File;
import java.io.IOException;

public class IMDBStepDefinition {

    private static final String IMDB_URL = "https://www.imdb.com/search/title/?title_type=feature&release_date=1950-01-01,2012-12-31&sort=num_votes,desc";
    private static final String DOWNLOAD_FOLDER_PATH = System.getProperty("user.dir")+ File.separator+"OutputFile";
    private static final String IMDB_WORKBOOK_NAME = "Output_Results.xlsx";
    private static final String IMDB_SHEET_NAME = "IMDB";
    WebDriver driver;

    SoftAssert softAssert;

    AdvancedTileSearchPage advancedTileSearchPage;
    @Before("@IMDBTest")
    public void setUpDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver =  new ChromeDriver(options);
        softAssert = new SoftAssert();
    }

    @After("@IMDBTest")
    public void closeBrowser(){
        driver.quit();
        softAssert.assertAll();
    }

    @Given("The User is in IMDB Advanced Tile Search window")
    public void the_user_is_in_imdb_advanced_tile_search_window() {
        driver.get(IMDB_URL);
        advancedTileSearchPage = new AdvancedTileSearchPage(driver ,softAssert);
    }

    @When("The User applies sort by criteria as {string}")
    public void the_user_applies_sort_by_criteria_as(String criteria) {
        advancedTileSearchPage.applySortCriteria(criteria);
    }

    @Then("Retrieve the list of top {int} movies")
    public void retrieve_the_list_of_top_movies(Integer count) throws IOException {
        advancedTileSearchPage.retrieveListOfTopNTileName(count);
    }
    @Then("Find out the average IMDB rating for top {int} movies")
    public void find_out_the_average_imdb_rating_for_top_movies(Integer count) {
        advancedTileSearchPage.findAverageRatingOfTopNTiles(count);
    }
    @Then("Find out the average running time of top {int} movies")
    public void find_out_the_average_running_time_of_top_movies(Integer count) {
        advancedTileSearchPage.findAverageTimeDurationOfTopNTiles(count);
    }

    @And("Store the results in output file")
    public void storeTheResultsInOutputFile() throws IOException, InvalidFormatException {
        String fileSource =  DOWNLOAD_FOLDER_PATH + File.separator + IMDB_WORKBOOK_NAME;
        ExcelUtil.writeDataToExcelFile(advancedTileSearchPage.outputRow , fileSource , IMDB_SHEET_NAME);
    }
}
