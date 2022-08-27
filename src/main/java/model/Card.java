package model;

import java.util.Properties;

public class Card {
    private final long number;
    private final String expirationDate;
    private final int cvc;

    public Card(Properties properties) {
        number = Long.parseLong(properties.getProperty("card.number"));
        expirationDate = properties.getProperty("card.expirationDate");
        cvc = Integer.parseInt(properties.getProperty("card.cvc"));
    }

    public Long getNumber() {
        return number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getCvc() {
        return cvc;
    }
}
