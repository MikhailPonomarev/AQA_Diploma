package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BuyTourOnCreditPage {
    final SelenideElement buyButton = $(Selectors.withText("Купить"));
    final SelenideElement formHeading = $(Selectors.withText("Кредит по данным карты"));
    final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    final SelenideElement monthField = $("[placeholder='08']");
    final SelenideElement yearField = $("[placeholder='22']");
    final SelenideElement holderField = $("div:nth-child(3) > span input:not([placeholder='999'])");
    final SelenideElement cvvField = $("[placeholder='999']");
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

    public BuyTourPage changeToBuyForm() {
        buyButton.click();
        return new BuyTourPage();
    }
}
