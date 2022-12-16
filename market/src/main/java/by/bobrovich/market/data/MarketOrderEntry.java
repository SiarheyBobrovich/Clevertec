package by.bobrovich.market.data;

import by.bobrovich.market.api.OrderEntry;

import java.util.Objects;

public record MarketOrderEntry(Integer id, Integer quantity) implements OrderEntry {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketOrderEntry that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }

    @Override
    public String toString() {
        return "MarketOrderEntry{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
