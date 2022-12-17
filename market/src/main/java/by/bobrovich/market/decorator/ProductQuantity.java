package by.bobrovich.market.decorator;

import by.bobrovich.market.api.Product;

import java.math.BigDecimal;

public class ProductQuantity implements Product {

    private final Product product;
    private final int quantity;

    public ProductQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return product.getId();
    }

    @Override
    public BigDecimal getPrice() {
        return product.getPrice();
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }

    @Override
    public boolean isDiscount() {
        return product.isDiscount();
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}