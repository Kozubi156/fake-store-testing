package page_objects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderReceivedPage extends BasePage {

    private WebDriverWait wait;

    public HeaderPage header;

    @FindBy(css = ".order>strong")
    private WebElement orderNumber;

    @FindBy(css = ".woocommerce-thankyou-order-received")
    private List<WebElement> orderReceivedMessages;

    public OrderReceivedPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 15);
        header = new HeaderPage(driver);
    }

    public boolean isOrderSuccessful() {
        wait.until(ExpectedConditions.urlContains("/zamowienie/zamowienie-otrzymane/"));
        int numberOfSuccessMessages =
            orderReceivedMessages.size();
        if (numberOfSuccessMessages == 1) {
            return true;
        } else if (numberOfSuccessMessages == 0) {
            return false;
        } else {
            throw new IllegalArgumentException("Number of success messages is " + numberOfSuccessMessages);
        }
    }
}
