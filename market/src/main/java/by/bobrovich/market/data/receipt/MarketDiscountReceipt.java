package by.bobrovich.market.data.receipt;

import by.bobrovich.market.decorator.BasketDiscountDecorator;
import by.bobrovich.market.entity.MarketDiscountCard;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MarketDiscountReceipt extends AbstractReceipt {

    protected final String totalFormat = """
            TAXABLE TOT_                        $%s
            VAT17%%                              $%s
            DISCOUNT                            $%s
            TOTAL                               $%s
            """;

    private final String total;

    public MarketDiscountReceipt(LocalDateTime dateTime,
                                 BasketDiscountDecorator basket,
                                 int cashier,
                                 MarketDiscountCard card) {
        super(dateTime, basket, cashier);
        total = createTotal(basket, card);
    }

    private String createTotal(BasketDiscountDecorator basket, MarketDiscountCard card) {
        BigDecimal totalPrice = basket.getTotalPrice();
        BigDecimal discount = basket.getDiscount(card.getDiscount());
        BigDecimal totalPriceWithDiscount = totalPrice.subtract(discount);

        return String.format(
                totalFormat,
                setScaleTo2(getTaxableTot(totalPrice)),
                setScaleTo2(getVat(totalPrice)),
                setScaleTo2(discount),
                setScaleTo2(totalPriceWithDiscount)
        );
    }
}