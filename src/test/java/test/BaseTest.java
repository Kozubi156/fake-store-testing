package test;

import drivers.DriverFactory;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
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
import org.openqa.selenium.io.FileHandler;
import utils.ConfigurationReader;
import utils.TestDataReader;
import utils.TestStatus;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseTest {
    public final String configurationLocation = "src/configs/Configurations.properties";
    private final String testDataLocation = "src/main/java/test_data/TestData.properties";
    protected WebDriver driver;
    protected ConfigurationReader configuration;
    protected TestDataReader testData;

    @RegisterExtension
    TestStatus status = new TestStatus();

    @BeforeAll
    public void getConfiguration() {
        configuration = new ConfigurationReader(configurationLocation);
        testData = new TestDataReader(testDataLocation);
    }

    @BeforeEach
    public void testSetUp() {
        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.create(configuration);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown(TestInfo info) throws IOException {
        if (status.isFailed) {
            String path = takeScreenshot(info);
            System.out.println("Test screenshot is available at: " + path);
            addScreenshotToReport(path);
        }
        driver.quit();
    }

    private String takeScreenshot(TestInfo info) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        File screenshotsDirectory = new File("screenshots/");
        if (!screenshotsDirectory.exists()) {
            screenshotsDirectory.mkdirs();
        }
        String path = "screenshots/" + info.getDisplayName() + " " + formatter.format(timeNow) + ".png";
        try {
            FileHandler.copy(screenshot, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private void addScreenshotToReport(String path) {
        InputStream stream = null;
        try {
            stream = Files.newInputStream(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Allure.addAttachment("Screenshot", stream);
    }
}
