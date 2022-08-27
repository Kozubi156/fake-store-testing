package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigurationReader;

public abstract class BasePage {

    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected By loaderLocator = By.cssSelector(".blockOverlay");
    protected BasePage(WebDriver driver) {
        configuration = new ConfigurationReader("src/main/java/test_data/TestData.properties");
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
}
