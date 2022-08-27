package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage extends BasePage {
    String addToCartButtonCssSelector = "a[data-product_id='{productId}']";
    private WebDriverWait wait;
    @FindBy(css = ".added_to_cart.wc-forward")
    private WebElement viewCartButton;

    public CategoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public CategoryPage addToCart(String productId) {
        WebElement addToCartButton =
                driver.findElement(By.cssSelector(addToCartButtonCssSelector.replace("{productId}", productId)));
        addToCartButton.click();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.attributeContains(addToCartButton, "class", "added"));
        return this;
    }

    public CartPage viewCart() {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton)).click();
        return new CartPage(driver);
    }
}
