package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page_objects.CartPage;
import page_objects.CheckoutPage;
import page_objects.MyAccountPage;
import page_objects.OrderReceivedPage;
import page_objects.ProductPage;

@DisplayName("Payment Tests")
public class PaymentTest extends BaseTest {
    boolean isMarkTermsAndCondition = true;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    public void shouldBuyWithoutCreateAccountTest() {
        ProductPage productPage = new ProductPage(driver)
                .gotTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.closeDemoNotice();
        CartPage cartPage = productPage.addToCart().viewCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        OrderReceivedPage orderReceivedPage = checkoutPage
                .typeFirstName(testData.getCustomer().getFirstName())
                .typeLastName(testData.getCustomer().getLastName())
                .chooseCountry(testData.getAddress().getCountryCode())
                .typeStreet(testData.getAddress().getStreet())
                .typePostCode(testData.getAddress().getPostalCode())
                .typeCity(testData.getAddress().getCity())
                .tyePhoneNumber(testData.getContact().getPhone())
                .typeEmail(testData.getContact().getEmail())
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpDate(testData.getCard().getExpirationDate())
                .typeCardCsv(testData.getCard().getCvc())
                .markTermsAndCondition(isMarkTermsAndCondition)
                .order();

        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "Order successful message wasn't found.");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreateAccountDuringPaymentTest() {
        ProductPage productPage = new ProductPage(driver)
                .gotTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.closeDemoNotice();
        CartPage cartPage = productPage.addToCart().viewCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        OrderReceivedPage orderReceivedPage = checkoutPage
                .typeFirstName(testData.getCustomer().getFirstName())
                .typeLastName(testData.getCustomer().getLastName())
                .chooseCountry(testData.getAddress().getCountryCode())
                .typeStreet(testData.getAddress().getStreet())
                .typePostCode(testData.getAddress().getPostalCode())
                .typeCity(testData.getAddress().getCity())
                .tyePhoneNumber(testData.getContact().getPhone())
                .typeEmail(testData.getContact().getEmail())
                .createNewAccount()
                .typePassword(testData.getAccount().getPassword())
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpDate(testData.getCard().getExpirationDate())
                .typeCardCsv(testData.getCard().getCvc())
                .markTermsAndCondition(isMarkTermsAndCondition)
                .order();

        orderReceivedPage.isOrderSuccessful();
        MyAccountPage myAccountPage = orderReceivedPage.header.goToMyAccountDetails();

        assertEquals(
                "Witaj "
                        + testData.getCustomer().getFirstName()
                        + " "
                        + testData.getCustomer().getLastName()
                        + " (nie jesteś "
                        + testData.getCustomer().getFirstName()
                        + " "
                        + testData.getCustomer().getLastName()
                        + "? Wyloguj się)",
                myAccountPage.getAccountName());

        myAccountPage.deleteMe();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    public void shouldLoginAndPurchaseTest() {
        ProductPage productPage = new ProductPage(driver)
                .gotTo(configuration.getBaseUrl() + testData.getProduct().getUrl());
        productPage.demoNotice.closeDemoNotice();
        CartPage cartPage = productPage.addToCart().viewCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout()
                .showLogin()
                .typeLoginUsername(testData.getAccount().getLogin())
                .typeLoginPassword(testData.getAccount().getPassword())
                .login();

        OrderReceivedPage orderReceivedPage = checkoutPage
                .typeCardNumber(testData.getCard().getNumber())
                .typeCardExpDate(testData.getCard().getExpirationDate())
                .typeCardCsv(testData.getCard().getCvc())
                .markTermsAndCondition(isMarkTermsAndCondition)
                .order();

        boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
        assertTrue(isOrderSuccessful, "Order successful message wasn't found.");
    }

//    @Test
//    @ParameterizedTest(name = "Customer field validation check: {1}")
//    @MethodSource({"test_data.CustomerAndCreditCardData#customerTestData"})
//    public void shouldDisplayCustomerDataFieldValidationTest() {
//        // TODO: Add steps;
//    }

    // TODO: add test case to verify order details: price, customer data etc.;
}
