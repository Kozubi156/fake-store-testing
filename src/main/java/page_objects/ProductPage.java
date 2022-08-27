package page_objects;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    public DemoFooterPage demoNotice;
    public HeaderPage header;
    @FindBy(name = "add-to-cart")
    private WebElement addToCartButton;
    @FindBy(css = ".woocommerce-message")
    private WebElement messageBar;
    @FindBy(css = "div.woocommerce-message>.button.wc-forward")
    private WebElement viewCartButton;
    @FindBy(css = ".input-text.qty.text")
    private WebElement productQuantityField;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
        demoNotice = new DemoFooterPage(driver);
        header = new HeaderPage(driver);
    }

    public ProductPage addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        wait.until(ExpectedConditions.visibilityOf(messageBar));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewCartButton);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        return this;
    }

    public CartPage viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
        return new CartPage(driver);
    }

    public ProductPage addToCart(int quantity) {
        wait.until(ExpectedConditions.elementToBeClickable(productQuantityField))
                .clear();
        productQuantityField.sendKeys(String.valueOf(quantity));
        return addToCart();
    }

    public List<String> addFewProductsToCart(int numberItems, List<String> links) {
        List<String> toursLinks = links.subList(0, numberItems);
        for (String toursLink : toursLinks) {
            driver.navigate().to(toursLink);
            addToCart();
            wait.until(ExpectedConditions.visibilityOf(messageBar));
        }
        return toursLinks;
    }

    public ProductPage gotTo(String url) {
        driver.navigate().to(url);
        return this;
    }
}
