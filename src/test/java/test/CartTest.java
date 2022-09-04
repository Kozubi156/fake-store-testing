package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import page_objects.CartPage;
import page_objects.HomePage;
import page_objects.ProductPage;
import test.BaseTest;

public class CartTest extends BaseTest {
    private int expectedNumberOfProducts = 1;

    @Test
    public void shouldAddProductToCartTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        boolean isProductInCart =
                homePage.goToProduct(testData.getProduct().getId()).addToCart().viewCart().isProductInCart(testData.getProduct().getId());
        assertTrue(isProductInCart, "A product with id: " + testData.getProduct().getId() + " has not been found in the cart");
    }

    @Test
    public void shouldAddProductFromCategoryPageTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        CartPage cartPage =
                homePage.goToCategory(testData.getCategoryName()).addToCart(testData.getProduct().getId()).viewCart();

        boolean productQuantityInCart = cartPage.isProductInCart(testData.getProduct().getId());
        int productsNumberInCart = cartPage.getProductsNumber();

        assertAll(
                () -> assertTrue(
                        productQuantityInCart, "A product with id: " + testData.getProduct().getId() + " has not been found in the cart"),
                () -> assertEquals(
                        expectedNumberOfProducts, productsNumberInCart, "Wrong number of product is in the cart"));
    }

    @Test
    public void shouldAddOneProductFewTimesTest() {
        HomePage homePage = new HomePage(driver).goToHomePage();
        homePage.demoNotice.closeDemoNotice();

        int quantity = 10;
        CartPage cartPage = homePage.goToProduct(testData.getProduct().getId()).addToCart(quantity).viewCart();

        int productQuantityInCart = cartPage.getProductQuantity(testData.getProduct().getId());
        int productsNumberInCart = cartPage.getProductsNumber();

        assertAll(
                () -> assertEquals(
                        quantity,
                        productQuantityInCart,
                        "The cart contains incorrect quantity of product id: " + testData.getProduct().getId()),
                () -> assertEquals(
                        expectedNumberOfProducts, productsNumberInCart, "Wrong number of product is in the cart"));
    }

    @Test
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
                "The cart contains incorrect quantity of product id: " + testData.getProduct().getId());
    }

    @Test
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
