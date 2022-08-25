package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private SelenideElement heading1 = $("[data-test-id='dashboard']");
    private SelenideElement heading2 = $("div#root h1");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement cardNumberField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer'] span");


    public MoneyTransferPage(){
        heading1.shouldBe(visible);
        heading2.shouldBe(visible);
    }

    public MoneyTransferPage moneyTransfer(int amount, String cardNumber) {
        amountField.setValue(String.valueOf(amount));
        cardNumberField.setValue(cardNumber);
        transferButton.click();
        return new MoneyTransferPage();
    }

}
