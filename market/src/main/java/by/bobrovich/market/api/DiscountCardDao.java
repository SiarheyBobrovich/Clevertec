package by.bobrovich.market.api;

import by.bobrovich.market.exceptions.DiscountCardNotFoundException;

import java.util.Optional;

public interface DiscountCardDao {

    /**
     * Returns saved Discount card
     * @param id - Discount card id
     * @return - Discount card if contains
     */
    Optional<DiscountCard> getById(Integer id);
}
