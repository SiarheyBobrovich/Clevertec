package by.bobrovich.market.util;

import by.bobrovich.market.entity.MarketDiscountCard;

public class DiscountCardBuilder {

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private int id = 132;
        private byte discount = 10;

        private Builder() {}
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder discount(byte discount) {
            this.discount = discount;
            return this;
        }

        public MarketDiscountCard build() {
            return new MarketDiscountCard(id, discount);
        }
    }
}
