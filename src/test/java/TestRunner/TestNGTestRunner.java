package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinition"},
        publish = true,
        monochrome = true,
        plugin= {"pretty","html:target/cucumber-reports.html"}
)

public class TestNGTestRunner extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    public Object[][] scenario(){
        return super.scenarios();
    }
}
