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
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        var buyTourOnCreditPage = buyTourPage.changeToCreditForm();
        buyTourOnCreditPage.changeToBuyForm();
    }

    @Test
    void sendEmptyForm() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(true,null,null,null,null,null);

        var cardFieldSub = BuyTourPage.getCardFieldSub();
        assertEquals("???????????????? ????????????", cardFieldSub);
        var monthFieldSub = BuyTourPage.getMonthFieldSub();
        assertEquals("???????????????? ????????????", monthFieldSub);
        var yearFieldSub = BuyTourPage.getYearFieldSub();
        assertEquals("???????????????? ????????????", yearFieldSub);
        var holderFieldSub = BuyTourPage.getHolderFieldSub();
        assertEquals("???????? ?????????????????????? ?????? ????????????????????", holderFieldSub);
        var cvvFieldSub = BuyTourPage.getCvvFieldSub();
        assertEquals("???????????????? ????????????", cvvFieldSub);
    }

    @Test
    void sendFormWithEmptyCardNumber() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
        var buyTourPage = paymentChoicePage.buyTourOnCash();
        buyTourPage.falseBuyTour(
                false,
                null,
                cardMonth(false, 2),
                cardYear(false, 1),
                holderName(false, "en"),
                cvvCode(true));

        var cardFieldSub = BuyTourPage.getCardFieldSub();
        assertEquals("???????????????? ????????????", cardFieldSub);
    }

    @Test
    void sendFormWithFalseCardNumber() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", cardFieldSub);
    }

    @Test
    void buyTourWithExpiredCard() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("?????????? ???????? ???????????????? ??????????", yearFieldSub);
    }

    @Test
    void buyTourWithEmptyMonthField() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", monthFieldSub);
    }

    @Test
    void buyTourWithEmptyYearField() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", yearFieldSub);
    }

    @Test
    void buyTourWithEmptyHolderField() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????? ?????????????????????? ?????? ????????????????????", holderFieldSub);
    }

    @Test
    void buyTourWithCyrillicHolderName() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", holderFieldSub);
    }

    @Test
    void buyTourWithNumericHolderField() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", holderFieldSub);
    }

    @Test
    void buyTourWithEmptyCvvField() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", cvvFieldSub);
    }

    @Test
    void buyTourWithFalseCvvCode() {
        var paymentChoicePage = open(testUrl(), PaymentChoicePage.class);
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
        assertEquals("???????????????? ????????????", cvvFieldSub);
    }
}
