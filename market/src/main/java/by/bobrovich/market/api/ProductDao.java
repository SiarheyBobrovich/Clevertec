package by.bobrovich.market.api;

import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.ProductNotFoundException;

import java.util.Optional;

public interface ProductDao {

    /**
     * Returns saved product
     *
     * @param id - product id
     * @return - Product if contains
     * @throws ProductNotFoundException - if not found
     */
    Optional<MarketProduct> getById(Integer id);

    /**
     * Check Product in repository
     * @param id - Product id
     * @return - true if exists
     */
    boolean exists(Integer id);

    /**
     * Check Product in repository
     * @param id - Product id
     * @param quantity - Product quantity
     * @return - true if exists, and quantity is available
     */
    boolean isQuantityAvailable(Integer id, Integer quantity);
}