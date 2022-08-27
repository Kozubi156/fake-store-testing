package model;

import java.util.Properties;

public class Contact {
    private final int phone;
    private final String email;

    public Contact(Properties properties) {
        phone = Integer.parseInt(properties.getProperty("contact.phone"));
        email = properties.getProperty("contact.emailAddress");
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
