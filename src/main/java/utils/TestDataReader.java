package utils;

import model.Account;
import model.Address;
import model.Card;
import model.Contact;
import model.Customer;
import model.Product;

public class TestDataReader extends FileReader {

    private String testDataLocation;
    private Product product;
    private String categoryName;
    private Customer customer;
    private Address address;
    private Contact contact;
    private Card card;

    private Account account;

    public TestDataReader(String testDataLocation) {
        super(testDataLocation);
        this.testDataLocation = testDataLocation;
    }

    public Product getProduct() {
        return product;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Account getAccount() {
        return account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Card getCard() {
        return card;
    }

    void loadData() {
        product = new Product(properties);
        customer = new Customer(properties);
        address = new Address(properties);
        contact = new Contact(properties);
        account = new Account(properties);
        card = new Card(properties);
        categoryName = properties.getProperty("category.name");
    }

    public String getTestDataLocation() {
        return testDataLocation;
    }
}
