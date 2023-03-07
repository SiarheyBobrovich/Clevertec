package by.bobrovich.market.cache.data.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ResponseAlcoholDto(
        Long id,
        String name,
        String country,
        double vol,
        BigDecimal price,
        int quantity
) {
}
