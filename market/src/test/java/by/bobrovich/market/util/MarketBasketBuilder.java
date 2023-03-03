package by.bobrovich.market.util;

import by.bobrovich.market.data.MarketBasket;

public class MarketBasketBuilder {

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Builder() {}

        public MarketBasket build() {
            MarketBasket marketBasket = new MarketBasket();
            marketBasket.addProduct(MarketProductBuilder.build(), 99);
            return marketBasket;
        }
    }
}
