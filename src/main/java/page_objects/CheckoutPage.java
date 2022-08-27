package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    protected By paymentPageFormLocator = By.name("checkout");
    protected WebDriverWait wait;
    @FindBy(id = "place_order")
    private WebElement orderButton;
    @FindBy(name = "billing_first_name")
    private WebElement billingFirstNameFiled;
    @FindBy(name = "billing_last_name")
    private WebElement billingLastNameFiled;
    @FindBy(name = "billing_address_1")
    private WebElement billingStreetFiled;
    @FindBy(name = "billing_postcode")
    private WebElement billingPostCode;
    @FindBy(name = "billing_city")
    private WebElement billingCityFiled;
    @FindBy(name = "billing_phone")
    private WebElement billingPhoneFiled;
    @FindBy(name = "billing_email")
    private WebElement billingEmailFiled;

    @FindBy(name = "billing_country")
    private WebElement billingCountryDropDown;
    @FindBy(name = "cardnumber")
    private WebElement cardNumberField;
    @FindBy(css = "div#stripe-card-element iframe")
    private WebElement cardNumberFrame;

    @FindBy(css = "div#stripe-exp-element iframe")
    private WebElement cardExpDateFrame;

    @FindBy(name = "exp-date")
    private WebElement cardExpField;

    @FindBy(css = "div#stripe-cvc-element iframe")
    private WebElement cardCvcFrame;

    @FindBy(name = "cvc")
    private WebElement cardCvcField;
    @FindBy(id = "terms")
    private WebElement termsCheckBox;

    @FindBy(id = "createaccount")
    private WebElement createAccountCheckBox;

    @FindBy(id = "account_password")
    private WebElement setPasswordFiled;

    @FindBy(className = "showlogin")
    private WebElement showloginButton;

    @FindBy(id = "username")
    private WebElement loginUsernameFiled;

    @FindBy(id = "password")
    private WebElement loginPasswordFiled;

    @FindBy(name = "login")
    private WebElement loginButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public CheckoutPage typeFirstName(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(billingFirstNameFiled))
                .sendKeys(firstName);
        return this;
    }

    public CheckoutPage typeLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(billingLastNameFiled))
                .sendKeys(lastName);
        return this;
    }

    public CheckoutPage typeStreet(String streetName) {
        wait.until(ExpectedConditions.elementToBeClickable(billingStreetFiled))
                .sendKeys(streetName);
        return this;
    }

    public CheckoutPage typePostCode(String postCode) {
        wait.until(ExpectedConditions.elementToBeClickable(billingPostCode))
                .sendKeys(postCode);
        return this;
    }

    public CheckoutPage typeCity(String cityName) {
        wait.until(ExpectedConditions.elementToBeClickable(billingCityFiled))
                .sendKeys(cityName);
        return this;
    }

    public CheckoutPage typeEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(billingEmailFiled))
                .sendKeys(email);
        return this;
    }

    public CheckoutPage tyePhoneNumber(int phoneNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(billingPhoneFiled))
                .sendKeys(String.valueOf(phoneNumber));
        return this;
    }

    public CheckoutPage chooseCountry(String countryCode) {
        Select countryDropDown =
                new Select(wait.until(ExpectedConditions.elementToBeClickable(billingCountryDropDown)));
        countryDropDown.selectByValue(countryCode);
        return this;
    }

    public CheckoutPage typeCardNumber(Long creditCardNumber) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        findElementInFrame(cardNumberFrame, cardNumberField).sendKeys(String.valueOf(creditCardNumber));
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardExpDate(String expirationDate) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        findElementInFrame(cardExpDateFrame, cardExpField).sendKeys(expirationDate);
        driver.switchTo().defaultContent();
        return this;
    }

    public CheckoutPage typeCardCsv(int csvNumber) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loaderLocator));
        findElementInFrame(cardCvcFrame, cardCvcField).sendKeys(String.valueOf(csvNumber));
        driver.switchTo().defaultContent();
        return this;
    }

    private WebElement findElementInFrame(WebElement frameLocator, WebElement elementLocator) {
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        return wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
    }

    public CheckoutPage markTermsAndCondition(Boolean isMark) {
        if (isMark) {
            termsCheckBox.click();
        }
        driver.switchTo().defaultContent();
        return this;
    }

    public OrderReceivedPage order() {
        orderButton.click();
        return new OrderReceivedPage(driver);
    }

    public CheckoutPage createNewAccount() {
        createAccountCheckBox.click();
        return this;
    }

    public CheckoutPage typePassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(setPasswordFiled)).sendKeys(password);
        return this;
    }

    public CheckoutPage showLogin(){
        showloginButton.click();
        return this;
    }

    public CheckoutPage typeLoginUsername(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(loginUsernameFiled))
            .sendKeys(firstName);
        return this;
    }

    public CheckoutPage typeLoginPassword(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(loginPasswordFiled))
            .sendKeys(firstName);
        return this;
    }

    public CheckoutPage login(){
        loginButton.click();
        return this;
    }
}

