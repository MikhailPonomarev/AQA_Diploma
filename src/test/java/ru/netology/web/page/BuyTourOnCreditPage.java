package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BuyTourOnCreditPage {
    final SelenideElement formHeading = $(Selectors.withText("Кредит по данным карты"));
    final SelenideElement cardNumberField = $(Selectors.withText("Номер карты"));
    final SelenideElement monthField = $(Selectors.withText("Месяц"));
    final SelenideElement yearField = $(Selectors.withText("Год"));
    final SelenideElement holderField = $(Selectors.withText("Владелец"));
    final SelenideElement cvvField = $(Selectors.withText("CVC/CVV"));
    final SelenideElement continueButton = $(Selectors.withText("Продолжить"));

    public BuyTourOnCreditPage() {
        formHeading.shouldBe(visible);
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        holderField.shouldBe(visible);
        cvvField.shouldBe(visible);
        continueButton.shouldBe(visible);
    }
}
