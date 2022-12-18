package by.bobrovich.market.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class MarketProduct {
    private int id;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean isDiscount;

    public MarketProduct() {
    }

    public MarketProduct(int id, String description, BigDecimal price, int quantity, boolean isDiscount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.isDiscount = isDiscount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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
        return id == that.id && quantity == that.quantity && isDiscount == that.isDiscount && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price, quantity, isDiscount);
    }
}