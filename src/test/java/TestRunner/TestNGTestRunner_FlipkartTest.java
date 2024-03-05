package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/FlipkartSearch.feature",
        glue = {"stepdefinition"},
        tags = "@FlipkartTest",
        publish = true,
        monochrome = true,
        plugin= {"pretty","html:target/cucumber-reports_FlipkartTest.html"}
)

public class TestNGTestRunner_FlipkartTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
