package by.bobrovich.market.data.alcohol.request;

import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Builder
public record RequestAlcoholDto(

        @NotNull
        @Pattern(regexp = "^([aA-zZ]+\\s?)+")
        String name,

        @NotNull
        @Pattern(regexp = "^[a-zA-Z]+")
        String country,

        @NotNull
        @Min(0)
        @Max(100)
        Double vol,

        @NotNull
        @Min(0)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer quantity
) {
}
