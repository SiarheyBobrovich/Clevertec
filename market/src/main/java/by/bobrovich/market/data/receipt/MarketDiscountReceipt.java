package by.bobrovich.market.data.receipt;

import by.bobrovich.market.api.Basket;
import by.bobrovich.market.api.DiscountCard;
import by.bobrovich.market.decorator.BasketProductQuantityDecorator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class MarketDiscountReceipt extends AbstractReceipt {

    protected final String totalFormat = """
            TAXABLE TOT_                        $%s
            VAT17%%                              $%s
            DISCOUNT                            $%s
            TOTAL                               $%s
            """;

    private final String total;

    public MarketDiscountReceipt(LocalDateTime dateTime,
                                 Basket basket,
                                 int cashier,
                                 DiscountCard card) {
        super(dateTime, basket, cashier);
        total = createTotal(basket, card);
    }

    private String createTotal(Basket basket, DiscountCard card) {
        final BigDecimal cardDiscount = BigDecimal.valueOf(card.getDiscount())
                .setScale(2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(100), RoundingMode.HALF_UP);
        final List<BasketProductQuantityDecorator> products = basket.getProducts();

        BigDecimal currentTotal = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;

        for (BasketProductQuantityDecorator product : products) {
            if (product.isDiscount() && product.getQuantity() > 4) {
                discount = discount.add(product.getTotalPrice().multiply(cardDiscount));
            }
            currentTotal = currentTotal.add(product.getTotalPrice());
        }

        return String.format(
                totalFormat,
                currentTotal.multiply(new BigDecimal("0.83")).setScale(2, RoundingMode.HALF_UP),
                currentTotal.multiply(new BigDecimal("0.17")).setScale(2, RoundingMode.HALF_UP),
                discount.setScale(2, RoundingMode.HALF_UP),
                currentTotal.subtract(discount).setScale(2, RoundingMode.HALF_UP)
        );
    }

    @Override
    public String getTotal() {
        return total;
    }
}