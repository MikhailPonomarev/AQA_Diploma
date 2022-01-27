package ru.netology.web.test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.page.PaymentChoicePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class BuyTourTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void buyTourWithApprovedCard() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.buyTour(
                cardNumber(true),
                cardMonth(false, 6),
                cardYear(false, 2),
                holderName(false, "en"),
                cvvCode(true));

        String actualStatus = paymentStatus();
        int actualAmount = paymentAmount();

        assertEquals("APPROVED", actualStatus);
        assertEquals(45000, actualAmount);
    }

    @Test
    void buyTourWithDeclinedCard() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.buyTour(
                cardNumber(false),
                cardMonth(false, 6),
                cardYear(false, 2),
                holderName(false, "en"),
                cvvCode(true));

        String actualStatus = paymentStatus();
        int actualAmount = paymentAmount();

        assertEquals("DECLINED", actualStatus);
        assertEquals(45000, actualAmount);
    }

    @Test
    void buyTourWithCurrentMonthAndYear() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.buyTour(
                cardNumber(true),
                cardMonth(false, 0),
                cardYear(false, 0),
                holderName(false, "en"),
                cvvCode(true));

        String actualStatus = paymentStatus();
        int actualAmount = paymentAmount();

        assertEquals("APPROVED", actualStatus);
        assertEquals(45000, actualAmount);
    }

    @Test
    void changeToCreditForm() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.changeToCreditForm();
        buyTourPage.changeToCreditForm();
    }
}
