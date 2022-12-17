package by.bobrovich.market.data;

import by.bobrovich.market.api.Basket;
import by.bobrovich.market.api.Product;
import by.bobrovich.market.decorator.BasketProductQuantityDecorator;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MarketBasket implements Basket {

    private final Map<Integer, BasketProductQuantityDecorator> products;

    public MarketBasket() {
        products = new HashMap<>();
    }

    @Override
    public List<BasketProductQuantityDecorator> getProducts() {
        return products.values().stream().toList();
    }

    @Override
    public void addProduct(Product product, int quantity) {
        int id = product.getId();
        quantity += !products.containsKey(id) ? 0 : products.get(id).getQuantity();

        products.put(
                id,
                new BasketProductQuantityDecorator(
                        BasketProduct.builder()
                                .setId(id)
                                .setDescription(product.getDescription())
                                .setPrice(product.getPrice())
                                .setDiscount(product.isDiscount())
                                .setQuantity(quantity)
                                .build()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketBasket that)) return false;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "MarketBasket{" +
                "products=" + products +
                '}';
    }
}