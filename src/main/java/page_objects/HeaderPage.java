package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends BasePage {

    @FindBy(css = ".cart-contents")
    private WebElement viewCart;

    @FindBy(id = "menu-item-201")
    private WebElement myAccountTab;

    protected HeaderPage(WebDriver driver) {
        super(driver);
    }

    public CartPage viewCart() {
        viewCart.click();
        return new CartPage(driver);
    }

    public MyAccountPage goToMyAccountDetails() {
        myAccountTab.click();
        return new MyAccountPage(driver);
    }
}
