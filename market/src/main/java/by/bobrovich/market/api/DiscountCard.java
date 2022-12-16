package by.bobrovich.market.api;

public interface DiscountCard {
    /**
     * Returns a card id
     * @return current card id
     */
    int getId();

    /**
     * Returns percentage discount 0~100%
     * @return percentage of discount
     */
    byte getDiscount();
}
