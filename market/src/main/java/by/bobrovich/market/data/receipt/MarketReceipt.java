package by.bobrovich.market.data.receipt;

import by.bobrovich.market.api.Basket;
import by.bobrovich.market.decorator.BasketProductQuantityDecorator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

public class MarketReceipt extends AbstractReceipt {
    protected final String totalFormat =
            """
            TAXABLE TOT_                        $%s
            VAT17%%                              $%s
            TOTAL                               $%s
            """;
    private final String total;

    public MarketReceipt(LocalDateTime dateTime, Basket basket, int cashier) {
        super(dateTime, basket, cashier);
        this.total = createTotal(basket);
    }

    private String createTotal(Basket basket) {
        BigDecimal total = countTotal(basket);
        return String.format(totalFormat,
                setScaleTo2(total.multiply(new BigDecimal("0.83"))),
                setScaleTo2(total.multiply(new BigDecimal("0.17"))),
                setScaleTo2(total)
        );
    }

    private BigDecimal countTotal(Basket basket) {
        List<BasketProductQuantityDecorator> products = basket.getProducts();
        return products.stream()
                .map(BasketProductQuantityDecorator::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getTotal() {
        return total;
    }
}