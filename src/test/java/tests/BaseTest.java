package tests;

import drivers.DriverFactory;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.WebDriver;
import utils.ConfigurationReader;
import utils.TestDataReader;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;
    private final String testDataLocation = "src/main/java/test_data/TestData.properties";
    public final String configurationLocation = "src/configs/Configurations.properties";

    @BeforeAll
    public void getConfiguration(){
        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
    }

    @BeforeEach
    public void testSetUp() {
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.create(configuration);


        //        WebDriverManager.chromedriver().setup();
        //        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
