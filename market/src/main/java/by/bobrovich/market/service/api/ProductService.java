package by.bobrovich.market.service.api;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.Receipt;
import by.bobrovich.market.validation.ValidOrder;

public interface ProductService {

    /**
     * Calculate and return bill for order
     * @param order User order
     * @return Bill
     */
    Receipt getReceipt(@ValidOrder Order order);
}
