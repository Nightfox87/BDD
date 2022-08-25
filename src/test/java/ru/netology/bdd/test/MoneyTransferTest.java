package ru.netology.bdd.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(0);
        int amount = 1000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getSecondCardInfo().getCardNumber());
        Assertions.assertEquals(dashboardPage.getCardBalance(0), balanceFirstBeforeTransfer + amount);
        Assertions.assertEquals(dashboardPage.getCardBalance(1), balanceSecondBeforeTransfer - amount);

    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstBeforeTransfer = dashboardPage.getCardBalance(0);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(1);
        int amount = 1000;
        moneyTransferPage.moneyTransfer(amount, DataHelper.getFirstCardInfo().getCardNumber());
        Assertions.assertEquals(dashboardPage.getCardBalance(0), balanceFirstBeforeTransfer - amount);
        Assertions.assertEquals(dashboardPage.getCardBalance(1), balanceSecondBeforeTransfer + amount);

    }

    @Test
    void shouldTransferMoneyFromSecondToFirstOverLimit() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceSecondBeforeTransfer = dashboardPage.getCardBalance(1);
        var moneyTransferPage = dashboardPage.cardRefill(0);
        moneyTransferPage.moneyTransfer(balanceSecondBeforeTransfer + 5000, DataHelper.getSecondCardInfo().getCardNumber());
        $(".notification__content").shouldBe(visible);

    }
}
