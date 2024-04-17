package utilities;

import common.StaticClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class DriverUtil {

    WebDriver driver;

    public DriverUtil(WebDriver driver){
        this.driver = driver;
    }

    public byte[] takeScreenShotInBytes(){
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        return screenshot.getScreenshotAs(OutputType.BYTES);
    }

    public String takeScreenShotInBase64(){
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        return screenshot.getScreenshotAs(OutputType.BASE64);
    }

    public String takeScreenShotAsFile(){
        try{
            File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String source = StaticClass.SCREENSHOT_PATH + Utility.timestamp() + ".jpg" ;
            File destinationFile = new File(source);
            FileUtils.copyFile(sourceFile , destinationFile);
            return destinationFile.getAbsolutePath();
        }catch (Exception e){
            return null;
        }
    }
}
