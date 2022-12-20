package by.bobrovich.market.factory;

import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.api.Basket;
import by.bobrovich.market.data.receipt.MarketReceipt;
import by.bobrovich.market.data.receipt.MarketDiscountReceipt;
import by.bobrovich.market.decorator.BasketDiscountDecorator;
import by.bobrovich.market.entity.MarketDiscountCard;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReceiptFactory {
    public Receipt create(Basket basket, MarketDiscountCard card, int cashier) {
        Receipt receipt;
        if (card == null) {
            receipt = createStandardReceipt(basket, cashier);
        }else {
            receipt = createDiscountReceipt(basket, cashier, card);
        }
        return receipt;
    }

    private Receipt createDiscountReceipt(Basket basket, int cashier, MarketDiscountCard card) {
        return new MarketDiscountReceipt(
                LocalDateTime.now(),
                new BasketDiscountDecorator(basket),
                cashier,
                card
        );
    }

    private Receipt createStandardReceipt(Basket basket, int cashier) {
        return new MarketReceipt(
                LocalDateTime.now(),
                basket,
                cashier
        );
    }
}