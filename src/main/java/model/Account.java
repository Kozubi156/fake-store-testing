package model;

import java.util.Properties;

public class Account {
    private String login;

    private String password;

    public Account(Properties properties) {
        this.login = properties.getProperty("account.login");
        this.password = properties.getProperty("account.password");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
