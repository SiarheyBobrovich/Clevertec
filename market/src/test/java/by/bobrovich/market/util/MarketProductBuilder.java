package by.bobrovich.market.util;

import by.bobrovich.market.entity.MarketProduct;

import java.math.BigDecimal;

public class MarketProductBuilder {

    public static MarketProduct build() {
        return MarketProduct.builder()
                .id(132)
                .description("description")
                .price(BigDecimal.valueOf(12.123))
                .quantity(15)
                .isDiscount(true)
                .build();
    }
}
