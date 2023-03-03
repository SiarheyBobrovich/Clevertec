package by.bobrovich.market.util;

import by.bobrovich.market.entity.MarketDiscountCard;

public class DiscountCardBuilder {

    public static MarketDiscountCard build() {
        return MarketDiscountCard.builder()
                .id(132)
                .discount((byte)10)
                .build();
    }
}
