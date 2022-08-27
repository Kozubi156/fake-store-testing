package old_non_pom_tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ShoppingCartTests extends BaseTest {

    By firstProductId = By.cssSelector("[data-product_id='389']");
    By secondProductId = By.cssSelector("[data-product_id='391']");
    By firstProductDetails = By.cssSelector(".post-389");
    By secondProductDetails = By.cssSelector(".post-391");
    By infoBar = By.cssSelector(".woocommerce-info");

    int numberOfFirstProductToOrder = 3;
    int numberOfSecondProductToOrder = 6;
    int products = 3;
    int expectedProductNumber = 1;
    int orderedProductNumber = 1;
    String expectedMessage;
    String msgWrongProductsNumber = "Wrong number of products in basket";
    String msgIncorrectErrorText = "Incorrect message text";
    String msgIncorrectTotalAmount = "Total amount of added torus is incorrect";

    String msgIncorrectSubTotalAmount = "Sub total amount of added torus is incorrect";
    String msgIncorrectProductPrice = "Price tour is incorrect";
    By productCartLink = By.cssSelector("tr.woocommerce-cart-form__cart-item.cart_item>td.product-name>a");

    By shopMainPageProductLink = By.cssSelector(".woocommerce-loop-product__link");

    @BeforeEach
    public void setUpTest() {
        driver.navigate().to("https://fakestore.testelka.pl/");
        driver.findElement(By.className("woocommerce-store-notice__dismiss-link"))
                .click();
    }

    @Test
    public void shouldAddProductFromProductDetailsPageTest() {
        openProductDetailsPage(firstProductDetails);
        addProductToCart();
        viewCart(productDetailsViewCartLink);
        assertAll(
                () -> assertTrue(
                        productIsDisplayed(firstProductId), "Product has not found in the cart: " + firstProductId),
                () -> assertEquals(orderedProductNumber, getNumberOfProduct(), msgWrongProductsNumber),
                () -> assertEquals(
                        expectedProductNumber, getNumberOfProductToOrder().size()),
                assertProductPriceAndSubtotal(firstProductDetails, orderedProductNumber, expectedFirstProductPrice),
                () -> assertEquals(
                        calculateExpectedProductsTotalAmount(calculateExpectedProductSubTotalAmount(
                                calculateExpectedProductPrice(expectedFirstProductPrice), orderedProductNumber)),
                        getOrderTotal(),
                        msgIncorrectTotalAmount));
    }

    @Test
    public void shouldAddProductFromCategoryPageTest() {
        gotToFirstCategory();
        addProductToCart(firstProductId);
        viewCart(productViewCartButton);
        assertAll(
                () -> assertTrue(productIsDisplayed(firstProductId)),
                () -> assertEquals(orderedProductNumber, getNumberOfProduct(), msgWrongProductsNumber),
                () -> assertEquals(
                        expectedProductNumber, getNumberOfProductToOrder().size(), msgWrongProductsNumber),
                assertProductPriceAndSubtotal(firstProductDetails, orderedProductNumber, expectedFirstProductPrice),
                () -> assertEquals(
                        calculateExpectedProductsTotalAmount(calculateExpectedProductSubTotalAmount(
                                calculateExpectedProductPrice(expectedFirstProductPrice), orderedProductNumber)),
                        getOrderTotal(),
                        msgIncorrectTotalAmount));
    }

    @Test
    public void shouldAddOneProductFewTimesFromProductsDetailsTest() {
        openProductDetailsPage(firstProductDetails);
        addProductToCart(numberOfFirstProductToOrder);
        viewCart(productDetailsViewCartLink);
        assertAll(
                () -> assertEquals(numberOfFirstProductToOrder, getNumberOfProduct(), msgWrongProductsNumber),
                assertProductPriceAndSubtotal(
                        firstProductDetails, numberOfFirstProductToOrder, expectedFirstProductPrice),
                () -> assertEquals(
                        calculateExpectedProductsTotalAmount(calculateExpectedProductSubTotalAmount(
                                calculateExpectedProductPrice(expectedFirstProductPrice), numberOfFirstProductToOrder)),
                        getOrderTotal(),
                        msgIncorrectTotalAmount));
    }

    @Test
    public void shouldAddFewOfTwoProductsTest() {
        openProductDetailsPage(firstProductDetails);
        addProductToCart(numberOfFirstProductToOrder);
        driver.navigate().to(mainPageUrl);
        openProductDetailsPage(secondProductDetails);
        addProductToCart(numberOfSecondProductToOrder);
        viewCart(productDetailsViewCartLink);
        assertAll(
                () -> assertEquals(
                        numberOfFirstProductToOrder,
                        getNumberOfProductInCart(getProductId(firstProductDetails)),
                        msgWrongProductsNumber),
                () -> assertEquals(
                        numberOfSecondProductToOrder,
                        getNumberOfProductInCart(getProductId(secondProductDetails)),
                        msgWrongProductsNumber),
                assertProductPriceAndSubtotal(
                        firstProductDetails, numberOfFirstProductToOrder, expectedFirstProductPrice),
                assertProductPriceAndSubtotal(
                        secondProductDetails, numberOfSecondProductToOrder, expectedSecondProductPrice),
                () -> assertEquals(
                        calculateExpectedProductsTotalAmount(
                                calculateExpectedProductSubTotalAmount(
                                        calculateExpectedProductPrice(expectedFirstProductPrice),
                                        numberOfFirstProductToOrder),
                                (calculateExpectedProductSubTotalAmount(
                                        calculateExpectedProductPrice(expectedSecondProductPrice),
                                        numberOfSecondProductToOrder))),
                        getOrderTotal(),
                        msgIncorrectTotalAmount));
    }

    @Test
    public void shouldAddFewVariousProductsTest() {
        List<String> addedTours = addFewProductsToCart(products);
        viewCart(productDetailsViewCartLink);
        assertEquals(addedTours, getProductLink(productCartLink), msgWrongProductsNumber);
    }

    @Test
    public void shouldChangeNumberOfCartItemsTest() {
        expectedMessage = "Koszyk zaktualizowany.";

        addProductToCart(firstProductId);
        viewCart(productViewCartButton);
        changeNumberCartItems(numberOfFirstProductToOrder);
        assertAll(
                () -> assertEquals(expectedMessage, getMessageText(messageBar), msgIncorrectErrorText),
                () -> assertEquals(numberOfFirstProductToOrder, getNumberOfProduct(), msgWrongProductsNumber),
                assertProductPriceAndSubtotal(
                        firstProductDetails, numberOfFirstProductToOrder, expectedFirstProductPrice),
                () -> assertEquals(
                        calculateExpectedProductSubTotalAmount(
                                getProductPriceFromCart(getProductId(firstProductDetails)),
                                numberOfFirstProductToOrder),
                        getOrderTotal(),
                        msgIncorrectTotalAmount));
    }

    @Test
    public void shouldRemoveProductFromBasketTest() {
        String tourTitle = "Wyspy Zielonego Przylądka - Sal";
        expectedMessage = "Twój koszyk jest pusty.";

        openProductDetailsPage(firstProductDetails);
        addProductToCart();
        viewCart(productDetailsViewCartLink);
        WebElement btnRemove = driver.findElement(firstProductId);
        btnRemove.click();
        waitForInformationMessageBar(messageBar);
        assertAll(
                () -> assertEquals(
                        "Usunięto: „" + tourTitle + "“. Cofnij?", getMessageText(messageBar), msgIncorrectErrorText),
                () -> assertEquals(expectedMessage, getMessageText(infoBar), msgIncorrectErrorText),
                () -> assertFalse(isElementDisplayed(firstProductId), "Product was not " + "removed from cart"));
    }

    @Test
    public void shouldRemoveOneOfTwoProductsFromBasketTest() {
        String tourTitle = "Wyspy Zielonego Przylądka - Sal";
        expectedMessage = "Twój koszyk jest pusty.";

        // TODO:       addFewProductsToCart(2); do może dopisania
        openProductDetailsPage(firstProductDetails);
        addProductToCart();
        openProductDetailsPage(secondProductDetails);
        addProductToCart();
        viewCart(productDetailsViewCartLink);
        WebElement btnRemove = driver.findElement(firstProductId);
        btnRemove.click();
        waitForInformationMessageBar(messageBar);
        assertAll(
                () -> assertEquals(
                        "Usunięto: „" + tourTitle + "“. Cofnij?", getMessageText(messageBar), msgIncorrectErrorText),
                () -> assertFalse(
                        isElementDisplayed(infoBar),
                        "Incorrect information" + " " + "was " + "displayed: " + expectedMessage),
                () -> assertFalse(isElementDisplayed(firstProductId), "Product was " + "not removed from cart"),
                () -> assertTrue(
                        isElementDisplayed(secondProductId),
                        "Second tour " + "added to" + " cart is not " + "displayed"));
    }

    private void changeNumberCartItems(int items) {
        changeNumberOfProduct(items);
        driver.findElement(By.name("update_cart")).click();
        waitForInformationMessageBar(messageBar);
    }

    private List<String> addFewProductsToCart(int numberItems) {
        List<String> toursLinks = getProductLink(shopMainPageProductLink).subList(0, numberItems);
        for (String toursLink : toursLinks) {
            driver.navigate().to(toursLink);
            addProductToCart();
            waitForInformationMessageBar(messageBar);
        }
        return toursLinks;
    }

    private List<WebElement> getNumberOfProductToOrder() {
        return driver.findElements(By.cssSelector(".woocommerce-cart-form__cart-item.cart_item"));
    }

    private List<String> getProductLink(By selector) {
        return driver.findElements(selector).stream()
                .map(t -> t.getAttribute("href"))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean productIsDisplayed(By product) {
        return driver.findElement(product).isDisplayed();
    }

    private double getOrderTotal() {
        return Double.parseDouble(removePriceFormat(driver.findElement(By.cssSelector("tr.cart-subtotal span.amount"))
                .getText()));
    }

    private int getNumberOfProduct() {
        return Integer.parseInt(
                driver.findElement(By.cssSelector(".input-text.qty.text")).getAttribute("value"));
    }

    private int getNumberOfProductInCart(String productId) {
        return Integer.parseInt(driver.findElement(
                        By.xpath(".//a[@data-product_id=" + productId + "]/ancestor::tr/td" + "//div//input"))
                .getAttribute("value"));
    }

    private Double getProductPriceFromCart(String productId) {
        return Double.parseDouble(removePriceFormat(driver.findElement((By.xpath(".//a[@data-product_id="
                        + productId
                        + "]/ancestor::tr/td[@class='product-price']//span[@class='woocommerce-Price-amount amount']")))
                .getText()));
    }

    private Double getProductSubtotalPriceFromCart(String productId) {
        return Double.parseDouble(removePriceFormat(driver.findElement(
                        By.xpath(
                                ".//a[@data-product_id="
                                        + productId
                                        + "]/ancestor::tr/td[@class='product-subtotal']//span[@class='woocommerce-Price-amount amount']"))
                .getText()));
    }

    private String getProductId(By selector) {
        return selector.toString().substring(22);
    }

    private Double changePriceToNumber(String price) {
        return Double.parseDouble(removePriceFormat(price));
    }

    private String removePriceFormat(String price) {
        return price.replaceAll(" ", "").replaceAll("zł", "").replaceAll(",", ".");
    }

    private double calculateExpectedProductSubTotalAmount(
            Double expectedFirstTourPrice, int numberOfFirstProductsToOrder) {
        return expectedFirstTourPrice * numberOfFirstProductsToOrder;
    }

    private double calculateExpectedProductsTotalAmount(Double... subtotalProductPrice) {
        double result = 0.0;
        for (Double Double : subtotalProductPrice) {
            result = result + Double;
        }
        return result;
    }

    private Double calculateExpectedProductPrice(String productPrice) {
        return changePriceToNumber(productPrice);
    }

    private Executable assertProductPriceAndSubtotal(
            By productDetailsLocator, int numberOfOrderedProduct, String productPrice) {
        return () -> assertAll(
                () -> assertEquals(
                        calculateExpectedProductSubTotalAmount(
                                getProductPriceFromCart(getProductId(productDetailsLocator)), numberOfOrderedProduct),
                        getProductSubtotalPriceFromCart(getProductId(productDetailsLocator)),
                        msgIncorrectSubTotalAmount),
                () -> assertEquals(
                        calculateExpectedProductPrice(productPrice),
                        getProductPriceFromCart(getProductId(productDetailsLocator)),
                        msgIncorrectProductPrice));
    }

    private void gotToFirstCategory() {
        WebElement firstCategorySection = driver.findElement(By.cssSelector("li.product-category.product.first"));
        firstCategorySection.click();
    }
}
