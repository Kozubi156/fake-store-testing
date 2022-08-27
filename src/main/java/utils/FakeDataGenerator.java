package utils;

import com.github.javafaker.Faker;

public class FakeDataGenerator {

    public static String randomEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }
}
