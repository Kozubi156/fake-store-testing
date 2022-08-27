package page_objects;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ConfigurationReader;

public class HomePage extends BasePage {

    public DemoFooterPage demoNotice;
    @FindBy(css = ".woocommerce-loop-product__link")
    private List<WebElement> productsLinks;

    private ConfigurationReader configurationReader;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        demoNotice = new DemoFooterPage(driver);
    }

    public HomePage goToHomePage() {
        configurationReader = new ConfigurationReader("src/configs/Configurations.properties");
        driver.navigate().to(configurationReader.getBaseUrl());
        return this;
    }

    public ProductPage goToProduct(String productId) {
        By productRedirection = By.cssSelector("li[class*='" + productId + "']");
        driver.findElement(productRedirection).click();
        return new ProductPage(driver);
    }

    public CategoryPage goToCategory(String categoryName) {
        driver.findElement(By.xpath(
                        ".//h2[@class='woocommerce-loop-category__title'][contains(text(), '" + categoryName + "')]"))
                .click();
        return new CategoryPage(driver);
    }

    public List<String> getProducts() {
        return productsLinks.stream()
                .map(t -> t.getAttribute("href"))
                .distinct()
                .collect(Collectors.toList());
    }
}
