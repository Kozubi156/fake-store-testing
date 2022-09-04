package utils;

public class ConfigurationReader extends FileReader {

    private String configurationLocation;
    private String baseUrl;
    private String hubUrl;
    private String browser;

    private boolean isRemote;

    public ConfigurationReader(String configurationLocation) {
        super(configurationLocation);
        this.configurationLocation = configurationLocation;
    }

    void loadData() {
        hubUrl = properties.getProperty("hubUrl");
        baseUrl = properties.getProperty("baseUrl");
        browser = properties.getProperty("browser");
        isRemote = Boolean.parseBoolean(properties.getProperty("isRemote"));
    }

    public String getBrowser() {
        return browser;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getConfigurationLocation() {
        return configurationLocation;
    }
}
