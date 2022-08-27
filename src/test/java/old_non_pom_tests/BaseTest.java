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

    String mainPageUrl = "https://fakestore.testelka.pl/";




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


    public boolean isElementDisplayed(By element) {
        return driver.findElements(element).size() != 0;
    }

    public void viewCart(By cartLocator) {
        WebElement openCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(cartLocator));
        openCartBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.shop_table_responsive.cart ")));
    }
}
