package page_objects;


import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends CartPage {

    private WebDriverWait wait;
    @FindBy(className = "delete-me")
    private WebElement deleteMeButton;

    @FindBy(css = ".woocommerce-MyAccount-content>p:first-of-type")
    private WebElement accountName;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 5);
    }

    public HomePage deleteMe() {
        deleteMeButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return new HomePage(driver);
    }

    public String getAccountName() {
        return accountName.getText();
    }
}
