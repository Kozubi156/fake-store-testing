package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoFooterPage extends BasePage {

    @FindBy(css = ".woocommerce-store-notice__dismiss-link")
    private WebElement demoNoticeBar;

    protected DemoFooterPage(WebDriver driver) {
        super(driver);
    }

    public void closeDemoNotice() {
        demoNoticeBar.click();
    }
}
