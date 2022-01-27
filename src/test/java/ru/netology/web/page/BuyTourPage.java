package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class BuyTourPage {
    final SelenideElement formHeading = $(Selectors.withText("Оплата по карте"));
    final SelenideElement cardNumberField = $(Selectors.withText("Номер карты"));
    final SelenideElement monthField = $(Selectors.withText("Месяц"));
    final SelenideElement yearField = $(Selectors.withText("Год"));
    final SelenideElement holderField = $(Selectors.withText("Владелец"));
    final SelenideElement cvvField = $(Selectors.withText("CVC/CVV"));
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
        successNotification.should(appear);
    }

    public void falseBuyTour(String cardNumber, String month, String year, String holderName, String cvvCode) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        holderField.setValue(holderName);
        cvvField.setValue(cvvCode);
        continueButton.click();
        errorNotification.should(appear);
    }
}

