package ru.netology.web.data;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    public static String requestUri(boolean isCredit) {
        if (!isCredit) {
            return "http://localhost:8080/api/v1/pay";
        } else {
            return "http://localhost:8080/api/v1/credit";
        }
    }

    public static String paymentBody(String cardNumber, String cardYear, String cardMonth, String holderName, String cvcCode) {
        return "{\"number\":" + "\"" + cardNumber + "\"" + ",\"year\":" + "\"" + cardYear + "\"" + ",\"month\":" +
                "\"" + cardMonth + "\"" + ",\"holder\":" + "\"" + holderName + "\"" + ",\"cvc\":" + "\"" +
                cvcCode + "\"" + "}";
    }

    public static String paymentRequest(String body, String uri, int statusCode) {
        return given()
                .headers("Content-Type", ContentType.JSON,
                "Accept", ContentType.JSON)
                .body(body)
                .when()
                .post(uri)
                .then()
                .statusCode(statusCode)
                .and().extract().path("status");
    }

    public static String errorRequest(String body, String uri, int statusCode) {
        return given()
                .headers("Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .body(body)
                .when()
                .post(uri)
                .then()
                .statusCode(statusCode)
                .and().extract().path("message");
    }
}
