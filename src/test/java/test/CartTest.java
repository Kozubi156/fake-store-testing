package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page_objects.CartPage;
import page_objects.HomePage;
import page_objects.ProductPage;

@DisplayName("Cart Tests")
public class CartTest extends BaseTest {
    private final int expectedNumberOfProducts = 1;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Add a product to cart from product details page")
    @Issue("issue-1")
    @TmsLink("TMS-1")
    @Link(name="Google", url="www.google.com", type = "issue")
    @Owner("Kozubi")
    public void shouldAddProductToCartTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        boolean isProductInCart = homePage.goToProduct(testData.getProduct().getId())
                .addToCart()
                .viewCart()
                .isProductInCart(testData.getProduct().getId());
        assertTrue(
                isProductInCart,
                "A product with id: " + testData.getProduct().getId() + " has not been found in the cart");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Add a product to cart from category page")
    public void shouldAddProductFromCategoryPageTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        CartPage cartPage = homePage.goToCategory(testData.getCategoryName())
                .addToCart(testData.getProduct().getId())
                .viewCart();

        boolean productQuantityInCart =
                cartPage.isProductInCart(testData.getProduct().getId());
        int productsNumberInCart = cartPage.getProductsNumber();

        assertAll(
                () -> assertTrue(
                        productQuantityInCart,
                        "A product with id: " + testData.getProduct().getId() + " has not been found in the cart"),
                () -> assertEquals(
                        expectedNumberOfProducts, productsNumberInCart, "Wrong number of product is in the cart"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add same product to cart a few times")
    public void shouldAddOneProductFewTimesTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        int quantity = 10;
        CartPage cartPage = homePage.goToProduct(testData.getProduct().getId())
                .addToCart(quantity)
                .viewCart();

        int productQuantityInCart =
                cartPage.getProductQuantity(testData.getProduct().getId());
        int productsNumberInCart = cartPage.getProductsNumber();

        assertAll(
                () -> assertEquals(
                        quantity,
                        productQuantityInCart,
                        "The cart contains incorrect quantity of product id: "
                                + testData.getProduct().getId()),
                () -> assertEquals(
                        expectedNumberOfProducts, productsNumberInCart, "Wrong number of product is in the cart"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Add few various products to cart")
    public void shouldAddFewVariousProductsTest() {
        int numberOfProducts = 10;
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();
        ProductPage productPage = new ProductPage(driver);

        List<String> addedProducts = productPage.addFewProductsToCart(numberOfProducts, homePage.getProducts());
        List<String> productsInCart = productPage.header.viewCart().getProducts();

        assertEquals(
                addedProducts,
                productsInCart,
                "Number of selected products is not equal to number of products in the cart");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Change number of added products to cart")
    public void shouldChangeNumberOfProductsTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        int productQuantity = 3;
        int productQuantityInCart = homePage.goToProduct(testData.getProduct().getId())
                .addToCart()
                .viewCart()
                .changeProductQuantity(testData.getProduct().getId(), productQuantity)
                .updateCart()
                .getProductQuantity(testData.getProduct().getId());

        // TODO: Add assertions to check product price, subtotal price and total price; ;
        assertEquals(
                productQuantity,
                productQuantityInCart,
                "The cart contains incorrect quantity of product id: "
                        + testData.getProduct().getId());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Remove added product from cart")
    public void shouldRemoveProductFromCartTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        boolean isProductInCart = homePage.goToProduct(testData.getProduct().getId())
                .addToCart()
                .viewCart()
                .removeProduct(testData.getProduct().getId())
                .isCartEmpty();

        assertTrue(isProductInCart, "The cart is not empty after removed the product");
    }
}
