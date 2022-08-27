package drivers;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigurationReader;

public class DriverFactory {

    public WebDriver create(ConfigurationReader configuration) {
        switch (Browser.valueOf(configuration.getBrowser())) {
            case CHROME:
                return getChromeDriver(configuration);
            case FIREFOX:
                return getFirefoxDriver(configuration);
            default:
                throw new IllegalArgumentException("Provided browser doesn't exist");
        }
    }

    private WebDriver getFirefoxDriver(ConfigurationReader configuration) {
        FirefoxOptions options = new FirefoxOptions();
        return getDriver(options, configuration);
    }

    private WebDriver getChromeDriver(ConfigurationReader configuration) {
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.VERSION, "66");
        return getDriver(options, configuration);
    }

    private WebDriver getDriver(MutableCapabilities options, ConfigurationReader configuration){
        try {
            return new RemoteWebDriver(new URL(configuration.getHubUrl()), options);
        } catch (MalformedURLException e) {
            System.out.println(e
                + " was thrown. The configuration file is incorrect or missing. Check the configuration file: "
                + configuration.getConfigurationLocation());
            throw new RuntimeException(e);
        }
    }
}
