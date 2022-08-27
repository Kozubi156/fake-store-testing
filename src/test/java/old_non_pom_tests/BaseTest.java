package old_non_pom_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestStatus;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;

    public JavascriptExecutor js;

    By orderReceivedText = By.className("entry-title");

    String assertionMsgIncorrectText = "Incorrect message text";
    By errorMsgBar = By.cssSelector("ul.woocommerce-error>li");
    String orderConfirmationTxt = "Zamówienie otrzymane";
    String orderDetailsTxt = "Szczegóły zamówienia";
    String tourName = "Wyspy Zielonego Przylądka - Sal";
    String expectedFirstProductPrice = "5 399,00 zł";
    String expectedSecondProductPrice = "3 200,00 zł";
    By productViewCartButton = By.cssSelector("a.added_to_cart.wc-forward");

    By productDetailsViewCartLink = By.cssSelector("a.button.wc-forward");

    String paymentMethod = "Karta debetowa/kredytowa (Stripe)";
    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy"));
    String orderQuantity = "× 1";

    By messageBar = By.cssSelector(".woocommerce-message");
    By orderConfirmation = By.className("woocommerce-thankyou-order-received");

    By creditCardFormFrame = By.cssSelector("div#stripe-card-element iframe");
    By productId = By.cssSelector("[data-product_id='389']");

    By cardNumberFrame = By.cssSelector("div#stripe-card-element iframe");
    By cardNumberField = By.name("cardnumber");

    By cardExpDateFrame = By.cssSelector("div#stripe-exp-element iframe");
    By cardExpFiled = By.name("exp-date");
    By cvcFrame = By.cssSelector("div#stripe-cvc-element iframe");
    By cvcField = By.name("cvc");

    String browser = "chrome";

    String mainPageUrl = "https://fakestore.testelka.pl/";

    @RegisterExtension
    TestStatus status = new TestStatus();

    @BeforeEach
    public void setUp() throws MalformedURLException {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("remote")) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.VERSION, "66");
            options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
        js = ((JavascriptExecutor) driver);
    }

    @AfterEach
    public void tearDown(TestInfo info) throws IOException {
        if (status.isFailed) {
            System.out.println("Test screenshot is available at: " + takeScreenshot(info));
        }
        driver.quit();
    }

    public void addProductToCart() {
        driver.findElement(By.name("add-to-cart")).click();
        waitForInformationMessageBar(messageBar);
    }

    public void addProductToCart(By productId) {
        driver.findElement(productId).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".added_to_cart.wc-forward")));
    }

    public void addProductToCart(int quantity) {
        changeNumberOfProduct(quantity);
        addProductToCart();
    }

    public void changeNumberOfProduct(int items) {
        driver.findElement(By.cssSelector(".input-text.qty.text")).clear();
        driver.findElement(By.cssSelector(".input-text.qty.text")).sendKeys(String.valueOf(items));
    }

    public void openProductDetailsPage(By selector) {
        driver.findElement(selector).click();
    }

    public void waitForInformationMessageBar(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getMessageText(By selector) {
        return driver.findElement(selector).getText();
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

    private WebElement findElementInFrame(By frameLocator, By elementLocator) {
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public void waitForOrderConfirmationPage() {
        waitForInformationMessageBar(orderConfirmation);
    }

    public void waitUntilSectionLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.blockUI.blockOverlay")));
    }

    public boolean isElementDisplayed(By element) {
        return driver.findElements(element).size() != 0;
    }

    public void viewCart(By cartLocator) {
        WebElement openCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(cartLocator));
        openCartBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.shop_table_responsive.cart ")));
    }
}
