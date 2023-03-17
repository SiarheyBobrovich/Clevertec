package by.bobrovich.market.data.receipt;

import by.bobrovich.market.api.Basket;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MarketReceipt extends AbstractReceipt {
    @JsonIgnore
    protected final String totalBlockFormat =
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
        BigDecimal total = basket.getTotalPrice();

        return String.format(totalBlockFormat,
                setScaleTo2(getTaxableTot(total)),
                setScaleTo2(getVat(total)),
                setScaleTo2(total)
        );
    }
}