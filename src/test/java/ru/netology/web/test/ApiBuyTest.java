package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static ru.netology.web.data.ApiHelper.*;
import static ru.netology.web.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiBuyTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void requestWithApprovedCard() {
        var body = paymentBody(cardNumber(true), cardYear(false,1),
                cardMonth(false,2), holderName(false,"en"), cvvCode(true));
        var expectedStatus = "APPROVED";

        var requestStatus = paymentRequest(body, requestUri(false), 200);
        assertEquals(expectedStatus, requestStatus);
        var dbStatus = paymentStatus();
        assertEquals(expectedStatus, dbStatus);
        var amount = paymentAmount();
        assertEquals(45000, amount);
    }

    @Test
    void requestWithDeclinedCard() {
        var body = paymentBody(cardNumber(false), cardYear(false,3),
                cardMonth(false,0), holderName(false,"en"), cvvCode(true));
        var expectedStatus = "DECLINED";

        var requestStatus = paymentRequest(body, requestUri(false), 200);
        assertEquals(expectedStatus, requestStatus);
        var dbStatus = paymentStatus();
        assertEquals(expectedStatus, dbStatus);
        var amount = paymentAmount();
        assertEquals(45000, amount);
    }

    @Test
    void requestWithCurrentMonthAndYear() {
        var body = paymentBody(cardNumber(true), cardYear(false,0),
                cardMonth(false,0), holderName(false,"en"), cvvCode(true));
        var expectedStatus = "APPROVED";

        var requestStatus = paymentRequest(body, requestUri(false), 200);
        assertEquals(expectedStatus, requestStatus);
        var dbStatus = paymentStatus();
        assertEquals(expectedStatus, dbStatus);
        var amount = paymentAmount();
        assertEquals(45000, amount);
    }

    @Test
    void sendEmptyBody() {
        var body = paymentBody(null,null,null,null,null);
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void sendBodyWithEmptyCardNumber() {
        var body = paymentBody(null, cardYear(false,0),
                cardMonth(false,0), holderName(false,"en"), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void sendBodyWithFalseCardNumber() {
        var body = paymentBody(falseCardNumber(), cardYear(false,0),
                cardMonth(false,0), holderName(false,"en"), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithExpiredCard() {
        var body = paymentBody(cardNumber(true), cardYear(true,2),
                cardMonth(true,6), holderName(false,"en"), cvvCode(true));
        var expectedStatus = "DECLINED";

        var requestStatus = paymentRequest(body, requestUri(false), 200);
        assertEquals(expectedStatus, requestStatus);
        var dbStatus = paymentStatus();
        assertEquals(expectedStatus, dbStatus);
        var amount = paymentAmount();
        assertEquals(45000, amount);
    }

    @Test
    void requestWithEmptyMonth() {
        var body = paymentBody(cardNumber(true), cardYear(false,2),
                null, holderName(false,"en"), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithEmptyYear() {
        var body = paymentBody(cardNumber(true), null, cardMonth(false,2),
                holderName(false,"en"), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithEmptyHolder() {
        var body = paymentBody(cardNumber(true), cardYear(false,1),
                cardMonth(false,2), null, cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithCyrillicHolderName() {
        var body = paymentBody(cardNumber(true), cardYear(false,5),
                cardMonth(false,1), holderName(false,"ru"), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithNumericHolderName() {
        var body = paymentBody(cardNumber(true), cardYear(false,3),
                cardMonth(false,4), holderName(true,null), cvvCode(true));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithEmptyCvv() {
        var body = paymentBody(cardNumber(true), cardYear(false,2),
                cardMonth(false,0), holderName(false,"en"), null);
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }

    @Test
    void requestWithFalseCvv() {
        var body = paymentBody(cardNumber(true), cardYear(false,0),
                cardMonth(false,3), holderName(false,"en"), cvvCode(false));
        var expectedMessage = "500 Internal Server Error";

        var requestMessage = errorRequest(body, requestUri(false), 500);
        assertEquals(expectedMessage, requestMessage);
    }
}
