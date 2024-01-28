package ru.netology.transference.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transference.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.\nПополнить";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text("**** **** **** " + cardInfo.getCardNumber().substring(15))).getText();
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var balance = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(balance);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferPage();
    }
}
