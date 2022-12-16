package by.bobrovich.market.data;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.OrderEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record MarketOrder(List<OrderEntry> orderEntries, Integer discountCardNumber) implements Order {

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketOrder that)) return false;
        return Objects.equals(orderEntries, that.orderEntries) && Objects.equals(discountCardNumber, that.discountCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderEntries, discountCardNumber);
    }

    @Override
    public String toString() {
        return "MarketOrder{" +
                "orderEntries=" + orderEntries +
                ", discountCardNumber=" + discountCardNumber +
                '}';
    }

    public static class Builder {
        private List<OrderEntry> orderEntries;
        private Integer discountCardNumber;

        public Builder addOrderEntry(OrderEntry entry) {
            if(orderEntries == null) {
                orderEntries = new ArrayList<>();
            }
            orderEntries.add(entry);
            return this;
        }

        public Builder addDiscountCard(Integer discountCardNumber) {
            this.discountCardNumber = discountCardNumber;
            return this;
        }

        public Order build() {
            return new MarketOrder(orderEntries, discountCardNumber);
        }
    }
}