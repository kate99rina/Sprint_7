package util;

import com.github.javafaker.Faker;

public class FakerData {
    private static final Faker FAKER = new Faker();

    public static String getLogin() {
        return FAKER.name().username();
    }

    public static String getPassword() {
        return FAKER.internet().password();
    }

    public static String getFirstName() {
        return FAKER.name().firstName();
    }

}
