package by.bobrovich.market.cache.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Alcohol {
    private Long id;
    private String name;
    private String country;
    private double vol;
    private BigDecimal price;
    private Integer quantity;
}
