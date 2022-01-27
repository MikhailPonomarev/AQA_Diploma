package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class BuyTourPage {
    final SelenideElement formHeading = $(Selectors.withText("Оплата по карте"));
    final SelenideElement buyOnCreditButton = $(Selectors.withText("Купить в кредит"));
    final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    final SelenideElement monthField = $("[placeholder='08']");
    final SelenideElement yearField = $("[placeholder='22']");
    final SelenideElement holderField = $("div:nth-child(3) > span input:not([placeholder='999'])");
    final SelenideElement cvvField = $("[placeholder='999']");
    final SelenideElement continueButton = $(Selectors.withText("Продолжить"));
    final SelenideElement successNotification = $(Selectors.withText("Успешно"));
    final SelenideElement errorNotification = $(Selectors.withText("Ошибка"));

    public BuyTourPage() {
        formHeading.shouldBe(visible);
    }

    public void buyTour(String cardNumber, String month, String year, String holderName, String cvvCode) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        holderField.setValue(holderName);
        cvvField.setValue(cvvCode);
        continueButton.click();
        successNotification.should(appear, Duration.ofSeconds(10));
    }

    public void falseBuyTour(String cardNumber, String month, String year, String holderName, String cvvCode) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        holderField.setValue(holderName);
        cvvField.setValue(cvvCode);
        continueButton.click();
        errorNotification.should(appear, Duration.ofSeconds(10));
    }

    public BuyTourOnCreditPage changeToCreditForm() {
        buyOnCreditButton.click();
        return new BuyTourOnCreditPage();
    }
}

