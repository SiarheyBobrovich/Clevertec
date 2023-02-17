package by.bobrovich.market.entity;

import java.util.Objects;

public class MarketDiscountCard {

    private Integer id;
    private Byte discount;

    public MarketDiscountCard() {
    }

    public MarketDiscountCard(Integer id, Byte discount) {
        setId(id);
        setDiscount(discount);
    }

    public int getId() {
        return id;
    }

    public byte getDiscount() {
        return discount;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Card id must not be null");
        }
        this.id = id;
    }

    public void setDiscount(Byte discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Card discount must not be null");
        } else if (discount < 0) {
            throw new IllegalArgumentException("Card discount must not be less then 0%");
        } else if (discount > 100) {
            throw new IllegalArgumentException("Card discount must not be more then 100%");
        }

        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketDiscountCard that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discount);
    }
}