package by.bobrovich.market.data;

import by.bobrovich.market.api.Basket;
import by.bobrovich.market.api.Product;
import by.bobrovich.market.decorator.ProductQuantity;

import java.util.*;

public class MarketBasket implements Basket {

    private final Map<Integer, ProductQuantity> products;

    public MarketBasket() {
        products = new HashMap<>();
    }

    @Override
    public List<ProductQuantity> getProducts() {
        return products.values().stream().toList();
    }

    @Override
    public void addProduct(Product product, int quantity) {
        final int id = product.getId();
        final int resultQuantity = !products.containsKey(id) ? quantity :
                products.get(id).getQuantity() + quantity;

        products.put(product.getId(), new ProductQuantity(product, resultQuantity));
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