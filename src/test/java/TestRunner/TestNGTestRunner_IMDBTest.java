package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/IMDBAdvancedSearch.feature",
        glue = {"stepdefinition"},
        tags = "@IMDBTest",
        publish = true,
        monochrome = true,
        plugin= {"pretty" ,"html:target/cucumber-reports_IMDBTest.html"}
)

public class TestNGTestRunner_IMDBTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
