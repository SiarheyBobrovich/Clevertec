package by.bobrovich.market.data.alcohol.response;

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
