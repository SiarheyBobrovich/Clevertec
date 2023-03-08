package by.bobrovich.market.data.product.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public record RequestProductDto(
        @NotNull(message = "Description must not be null")
        @Pattern(regexp = "^([a-zA-Z0-9а-яА-ЯёЁ]+\\s?)+")
        String description,

        @NotNull(message = "Price must not be null")
        @DecimalMin(value = "0.01", message = "Incorrect price, must be more then 0.01")
        BigDecimal price,
        @NotNull(message = "Quantity must not be null")
        @Min(value = 0, message = "Quantity must be 0 or more")
        Integer quantity,
        @NotNull(message = "isDiscount must not be null")
        Boolean isDiscount
        ) {
}
