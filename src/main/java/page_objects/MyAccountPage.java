package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends CartPage {

    @FindBy(className = "delete-me")
    private WebElement deleteMeButton;

    @FindBy(css = ".woocommerce-MyAccount-content>p:first-of-type")
    private WebElement accountName;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public HomePage deleteMe() {
        deleteMeButton.click();
        driver.switchTo().alert().accept();
        return new HomePage(driver);
    }

    public String getAccountName() {
        return accountName.getText();
    }
}
