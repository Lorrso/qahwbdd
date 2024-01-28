package ru.netology.transference.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.transference.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement fromInput = $("[data-test-id=from] input");
    private final SelenideElement errorMessage = $("[data-test-id=error-notification] .notification__content");


    public TransferPage() {
        SelenideElement transferHead = $(byText("Пополнение карты")).shouldBe(Condition.visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void makeTransferNoAmount(DataHelper.CardInfo cardInfo) {
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void makeTransferNoCard(String amountToTransfer) {
            amountInput.setValue(amountToTransfer);
            transferButton.click();
        }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldBe(Condition.visible).shouldHave(Condition.text(expectedText), Duration.ofSeconds(15));
    }
}
