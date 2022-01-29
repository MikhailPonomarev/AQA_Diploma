package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.page.BuyTourPage;
import ru.netology.web.page.PaymentChoicePage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class BuyTourTest {

        //TODO: не формируется allure отчет
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
                true,
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
                false,
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
                true,
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
        var buyTourOnCreditPage = buyTourPage.changeToCreditForm();
        buyTourOnCreditPage.changeToBuyForm();
    }

    @Test
    void sendEmptyForm() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(true,null,null,null,null,null);

        var cardFieldSub = BuyTourPage.getCardFieldSub();
        assertEquals("Неверный формат", cardFieldSub);
        var monthFieldSub = BuyTourPage.getMonthFieldSub();
        assertEquals("Неверный формат", monthFieldSub);
        var yearFieldSub = BuyTourPage.getYearFieldSub();
        assertEquals("Неверный формат", yearFieldSub);
        var holderFieldSub = BuyTourPage.getHolderFieldSub();
        assertEquals("Поле обязательно для заполнения", holderFieldSub);
        var cvvFieldSub = BuyTourPage.getCvvFieldSub();
        assertEquals("Неверный формат", cvvFieldSub);
    }

    @Test
    void sendFormWithEmptyCardNumber() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                null,
                cardMonth(false, 2),
                cardYear(false, 1),
                holderName(false, "en"),
                cvvCode(true));

        var cardFieldSub = BuyTourPage.getCardFieldSub();
        assertEquals("Неверный формат", cardFieldSub);
    }

    @Test
    void sendFormWithFalseCardNumber() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                falseCardNumber(),
                cardMonth(false, 1),
                cardYear(false, 0),
                holderName(false, "en"),
                cvvCode(true)
        );

        var cardFieldSub = BuyTourPage.getCardFieldSub();
        assertEquals("Неверный формат", cardFieldSub);
    }

    @Test
    void buyTourWithExpiredCard() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(true, 6),
                cardYear(true, 1),
                holderName(false, "en"),
                cvvCode(true)
        );

        var yearFieldSub = BuyTourPage.getYearFieldSub();
        assertEquals("Истёк срок действия карты", yearFieldSub);
    }

    @Test
    void buyTourWithEmptyMonthField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                null,
                cardYear(false, 3),
                holderName(false, "en"),
                cvvCode(true)
        );

        var monthFieldSub = BuyTourPage.getMonthFieldSub();
        assertEquals("Неверный формат", monthFieldSub);
    }

    @Test
    void buyTourWithEmptyYearField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 5),
                null,
                holderName(false, "en"),
                cvvCode(true)
        );

        var yearFieldSub = BuyTourPage.getYearFieldSub();
        assertEquals("Неверный формат", yearFieldSub);
    }

    @Test
    void buyTourWithEmptyHolderField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 7),
                cardYear(false, 0),
                null,
                cvvCode(true)
        );

        var holderFieldSub = BuyTourPage.getHolderFieldSub();
        assertEquals("Поле обязательно для заполнения", holderFieldSub);
    }

    @Test
    void buyTourWithCyrillicHolderName() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 1),
                cardYear(false, 3),
                holderName(false, "ru"),
                cvvCode(true)
        );

        var holderFieldSub = BuyTourPage.getHolderFieldSub();
        assertEquals("Неверный формат", holderFieldSub);
    }

    @Test
    void buyTourWithNumericHolderField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 1),
                cardYear(false, 3),
                holderName(true, null),
                cvvCode(true)
        );

        var holderFieldSub = BuyTourPage.getHolderFieldSub();
        assertEquals("Неверный формат", holderFieldSub);
    }

    @Test
    void buyTourWithEmptyCvvField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 9),
                cardYear(false, 0),
                holderName(false, "en"),
                null
        );

        var cvvFieldSub = BuyTourPage.getCvvFieldSub();
        assertEquals("Неверный формат", cvvFieldSub);
    }

    @Test
    void buyTourWithFalseCvvField() {
        var paymentChoicePage = open("http://localhost:8080/", PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                cardNumber(true),
                cardMonth(false, 9),
                cardYear(false, 0),
                holderName(false, "en"),
                cvvCode(false)
        );

        var cvvFieldSub = BuyTourPage.getCvvFieldSub();
        assertEquals("Неверный формат", cvvFieldSub);
    }
}
