package tests;

import drivers.DriverFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigurationReader;
import utils.TestDataReader;
import utils.TestStatus;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;
    private final String testDataLocation = "src/main/java/test_data/TestData.properties";
    public final String configurationLocation = "src/configs/Configurations.properties";

    @RegisterExtension
    TestStatus status = new TestStatus();
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
    public void tearDown(TestInfo info) throws IOException {
        if (status.isFailed) {
            System.out.println("Test screenshot is available at: " + takeScreenshot(info));
        }
        driver.quit();
    }

    private String takeScreenshot(TestInfo info) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String path = "C:\\Users\\lenovo\\Desktop\\Programowanie\\Kursy\\testelka.pl\\failed_tests_screenshot\\"
            + info.getDisplayName().replaceAll(": | ", "_") + "_" + formatter.format(timeNow)
            + ".jpg";
        FileUtils.copyFile(screenshot, new File(path));
        return path;
    }
}
