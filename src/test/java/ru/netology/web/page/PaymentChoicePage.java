package ru.netology.web.page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PaymentChoicePage {
    final SelenideElement pageHeading = $(Selectors.withText("Путешествие дня"));
    final SelenideElement tourHeading = $(Selectors.withText("Марракэш"));
    final SelenideElement buyButton = $(Selectors.withText("Купить"));
    final SelenideElement buyOnCreditButton = $(Selectors.withText("Купить в кредит"));

    public PaymentChoicePage() {
        pageHeading.shouldBe(visible);
        tourHeading.shouldBe(visible);
    }

    public BuyTourPage buyTourOnCash() {
        buyButton.click();
        return new BuyTourPage();
    }

    public BuyTourOnCreditPage boyTourOnCredit() {
        buyOnCreditButton.click();
        return new BuyTourOnCreditPage();
    }
}
