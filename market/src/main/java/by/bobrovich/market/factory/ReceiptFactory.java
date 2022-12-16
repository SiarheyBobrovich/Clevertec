package by.bobrovich.market.factory;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Basket;
import by.bobrovich.market.api.DiscountCard;
import by.bobrovich.market.data.receipt.MarketReceipt;
import by.bobrovich.market.data.receipt.MarketDiscountReceipt;

import java.time.LocalDateTime;

public class ReceiptFactory {
    public Receipt create(Basket basket, DiscountCard card, int cashier) {
        Receipt receipt;
        if (card == null) {
            receipt = createStandardReceipt(basket, cashier);
        }else {
            receipt = createDiscountReceipt(basket, cashier, card);
        }
        return receipt;
    }

    private Receipt createDiscountReceipt(Basket basket, int cashier, DiscountCard card) {
        return new MarketDiscountReceipt(LocalDateTime.now(), basket, cashier, card);
    }

    private Receipt createStandardReceipt(Basket basket, int cashier) {
        return new MarketReceipt(LocalDateTime.now(), basket, cashier);
    }
}