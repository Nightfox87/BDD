package ru.netology.bdd.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.bdd.data.DataHelper;

import java.awt.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement heading1 = $("[data-test-id='dashboard']");
    private SelenideElement heading2 = $(byText("Ваши карты"));
    private ElementsCollection cards = $$(".list__item div");
    //private ElementsCollection buttons = $$("[data-test-id='action-deposit' span]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading1.shouldBe(visible);
        heading2.shouldBe(visible);
    }

    public int getCardBalance(int id) {
        val text = cards.get(id).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage cardRefill(int id) {
        cards.get(id).$(byText("Пополнить")).click();
        return new MoneyTransferPage();
    }

}
