package by.bobrovich.market.entity;

import by.bobrovich.market.api.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class MarketProduct implements Product {
    private int id;

    private String description;
    private BigDecimal price;
    private boolean isDiscount;

    public MarketProduct() {
    }

    public MarketProduct(int id, String description, BigDecimal price, boolean isDiscount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.isDiscount = isDiscount;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketProduct that)) return false;
        return id == that.id && isDiscount == that.isDiscount && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, isDiscount);
    }

    @Override
    public String toString() {
        return "MarketProduct{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isDiscount=" + isDiscount +
                '}';
    }
}