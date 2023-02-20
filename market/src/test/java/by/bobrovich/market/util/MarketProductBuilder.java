package by.bobrovich.market.util;

import by.bobrovich.market.entity.MarketProduct;

import java.math.BigDecimal;

public class MarketProductBuilder {

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private int id = 132;
        private String description = "description";
        private BigDecimal price = BigDecimal.valueOf(12.123);
        private int quantity = 15;
        private boolean isDiscount = true;

        private Builder() {}
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setDiscount(boolean discount) {
            isDiscount = discount;
            return this;
        }

        public MarketProduct build() {
            return new MarketProduct(id, description, price, quantity, isDiscount);
        }
    }
}
