package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    public static String cardNumber(boolean isApproved) {
        if (isApproved) {
            return "1111 2222 3333 4444";
        } else {
            return "5555 6666 7777 8888";
        }
    }

    public static String cardMonth (boolean isExpired, long months) {
        if (!isExpired) {
            return LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern("MM"));
        } else {
            return LocalDate.now().minusMonths(months).format(DateTimeFormatter.ofPattern("MM"));
        }
    }

    public static String cardYear (boolean isExpired, long years) {
        if (!isExpired) {
            return LocalDate.now().plusYears(years).format(DateTimeFormatter.ofPattern("yy"));
        } else {
            return LocalDate.now().minusYears(years).format(DateTimeFormatter.ofPattern("yy"));
        }
    }

    public static String holderName(boolean isNumeric, String setLocale) {
        if (!isNumeric) {
            Faker faker = new Faker(new Locale(setLocale));
            return faker.name().firstName() + " " + faker.name().lastName();
        } else {
            return "4213&$^@#";
        }
    }

    public static String cvvCode(boolean isValid) {
        Faker faker = new Faker();
        if (isValid) {
            return faker.number().digits(3);
        } else {
            return faker.number().digits(2);
        }
    }

    @SneakyThrows
    public static String paymentStatus() {
        String query = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String status = null;

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres", "admin", "password"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString(1);
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static int paymentAmount() {
        String query = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1;";
        int amount = 0;

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres", "admin", "password"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    amount = resultSet.getInt(1);
                }
            }
        }
        return amount/100;
    }
}
