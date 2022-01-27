package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.page.PaymentChoicePage;

import static ru.netology.web.data.DataHelper.*;

public class PageTest {
    @Test
    void test() {
        var amount = paymentAmount();
        System.out.println(amount);
    }
}
