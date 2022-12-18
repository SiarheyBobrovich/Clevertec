package by.bobrovich.market.data.receipt;

import by.bobrovich.market.api.Basket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketReceipt extends AbstractReceipt {
    protected final String totalBlockFormat =
            """
            TAXABLE TOT_                        $%s
            VAT17%%                              $%s
            TOTAL                               $%s
            """;
    private final String totalBlock;

    public MarketReceipt(LocalDateTime dateTime, Basket basket, int cashier) {
        super(dateTime, basket, cashier);
        this.totalBlock = createTotal(basket);
    }

    private String createTotal(Basket basket) {
        BigDecimal total = basket.getTotalPrice();

        return String.format(totalBlockFormat,
                setScaleTo2(getTaxableTot(total)),
                setScaleTo2(getVat(total)),
                setScaleTo2(total)
        );
    }

    @Override
    public String getTotalBlock() {
        return totalBlock;
    }
}