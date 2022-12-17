package by.bobrovich.market.api;

import by.bobrovich.market.decorator.BasketProductQuantityDecorator;

import java.util.List;

public interface Basket {

    /**
     * Returns List of Products-quantities
     * @return - List of products
     */
    List<BasketProductQuantityDecorator> getProducts();

    /**
     * Add a new product to current basket
     * @param product - A new product
     * @param quantity - A quantity of products
     */
    void addProduct(Product product, int quantity);
}
