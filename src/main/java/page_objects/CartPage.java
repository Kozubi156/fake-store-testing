package page_objects;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    protected CheckoutPage checkoutPage;

    @FindBy(css = "table.shop_table_responsive.cart")
    private WebElement shopTable;

    @FindBy(css = "table.shop_table_responsive.cart")
    private List<WebElement> shopTables;

    @FindBy(css = ".woocommerce-cart-form__cart-item.cart_item")
    private List<WebElement> products;

    @FindBy(name = "update_cart")
    private WebElement updateCartButton;

    @FindBy(css = "tr.woocommerce-cart-form__cart-item.cart_item>td.product-name>a")
    private List<WebElement> productsLinks;

    @FindBy(css = "a.wc-forward")
    private WebElement goToPaymentButton;

    private String removeProductBtxXpathLocator = ".//a[@data-product_id='{productId}']";
    private String quantityFieldXpathLocator = "/ancestor::tr/td//div//input";

    @FindBy(css = ".woocommerce-message")
    private WebElement messageBar;

    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 7);
        checkoutPage = new CheckoutPage(driver);
    }

    public boolean isProductInCart(String productId) {
        waitForShopTable();
        By removeProductButton = By.xpath(setProductIdInLocator(productId));
        int productRecords = driver.findElements(removeProductButton).size();
        boolean presenceOfProduct = false;
        if (productRecords == 1) {
            presenceOfProduct = true;
        } else if (productRecords > 1) {
            throw new IllegalArgumentException("There is more than one record fot the product in cart");
        }
        return presenceOfProduct;
    }

    public int getProductsNumber() {
        waitForShopTable();
        return products.size();
    }

    public int getProductQuantity(String productId) {
        waitForShopTable();
        String quantityString = driver.findElement(
                        By.xpath(setProductIdInLocator(productId) + quantityFieldXpathLocator))
                .getAttribute("value");
        return Integer.parseInt(quantityString);
    }

    private void waitForShopTable() {
        wait.until(ExpectedConditions.visibilityOf(shopTable));
    }

    private String setProductIdInLocator(String productId) {
        return removeProductBtxXpathLocator.replace("{productId}", productId);
    }

    public List<String> getProducts() {
        waitForShopTable();
        return productsLinks.stream()
                .map(p -> p.getAttribute("href"))
                .distinct()
                .collect(Collectors.toList());
    }

    public CartPage changeProductQuantity(String productId, int quantity) {
        WebElement productQuantityField =
                driver.findElement(By.xpath(setProductIdInLocator(productId) + quantityFieldXpathLocator));
        productQuantityField.clear();
        productQuantityField.sendKeys(String.valueOf(quantity));
        return this;
    }

    public CartPage updateCart() {
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButton)).click();
        wait.until(ExpectedConditions.visibilityOf(messageBar));
        return this;
    }

    public CartPage removeProduct(String productId) {
        driver.findElement(By.xpath(setProductIdInLocator(productId))).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        return this;
    }

    public boolean isCartEmpty() {
        int shopTableNumber = shopTables.size();
        if (shopTableNumber == 1) {
            return false;
        } else if (shopTableNumber == 0) {
            return true;
        } else {
            throw new IllegalArgumentException("Wrong number of shop table element: there can be only one or none");
        }
    }

    public CheckoutPage goToCheckout() {
        goToPaymentButton.click();
        wait.until((ExpectedConditions.visibilityOfElementLocated(checkoutPage.paymentPageFormLocator)));
        return new CheckoutPage(driver);
    }
}
