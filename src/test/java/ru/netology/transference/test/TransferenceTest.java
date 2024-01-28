package ru.netology.transference.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.transference.data.DataHelper;
import ru.netology.transference.pages.DashboardPage;
import ru.netology.transference.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.transference.data.DataHelper.*;

public class TransferenceTest {
    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCardInfo;
    DataHelper.CardInfo secondCardInfo;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var amount = generateValidAmount(dashboardPage.getCardBalance(firstCardInfo));
        var expectedBalanceFirst = dashboardPage.getCardBalance(firstCardInfo) - amount;
        var expectedBalanceSecond = dashboardPage.getCardBalance(secondCardInfo) + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirst = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecond = dashboardPage.getCardBalance(secondCardInfo);
        assertAll(() -> assertEquals(expectedBalanceFirst, actualBalanceFirst),
                () -> assertEquals(expectedBalanceSecond, actualBalanceSecond));
    }
}
