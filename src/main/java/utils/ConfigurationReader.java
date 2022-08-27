package utils;

public class ConfigurationReader extends FileReader {

    private String configurationLocation;
    private String baseUrl;
    private String hubUrl;
    private String browser;

    public ConfigurationReader(String configurationLocation) {
        super(configurationLocation);
        this.configurationLocation = configurationLocation;
    }

    void loadData() {
        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");
    }

    public String getBrowser() {
        return browser;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }
}
