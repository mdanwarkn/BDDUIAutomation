package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/BOB.feature",
        glue = {"stepdefinition"},
        tags = "@BOBQuarterlyPayoutTest",
        publish = true,
        dryRun = false,
        monochrome = true,
        plugin= {"pretty","html:target/cucumber-reports_BOBTest.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)

public class TestNGTestRunner_BOBTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
