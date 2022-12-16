package by.bobrovich.market.api;

import java.math.BigDecimal;

public interface Product {

    /**
     * Returns id of current product
     * @return product id
     */
    int getId();


    /**
     * Returns a price of current product
     * @return price
     */
    BigDecimal getPrice();

    /**
     * Returns product description
     * @return description
     */
    String getDescription();

    boolean isDiscount();
}
