package model;

import java.util.Properties;

public class Customer {
    private final String firstName;
    private final String lastName;

    public Customer(Properties properties) {
        firstName = properties.getProperty("customer.firstName");
        lastName = properties.getProperty("customer.lastName");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
