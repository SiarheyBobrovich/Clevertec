package by.bobrovich.market.data;

import by.bobrovich.market.api.Basket;
import by.bobrovich.market.api.Product;
import by.bobrovich.market.decorator.ProductQuantity;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class MarketBasket implements Basket {

    private List<ProductQuantity> products;

    public MarketBasket() {
        products = new ArrayList<>();
    }

    @Override
    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantity> products) {
        this.products = products;
    }

    @Override
    public void addProduct(Product product, int quantity) {
        products.add(new ProductQuantity(product, quantity));
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