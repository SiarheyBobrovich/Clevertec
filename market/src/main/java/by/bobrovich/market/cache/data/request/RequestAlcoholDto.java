package by.bobrovich.market.cache.data.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestAlcoholDto(
        String name,
        String country,
        double vol,
        BigDecimal price,
        int quantity
) {
}
