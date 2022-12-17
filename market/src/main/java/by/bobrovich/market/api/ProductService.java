package by.bobrovich.market.api;

public interface ProductService {

    /**
     * Calculate and return bill for order
     * @param order User order
     * @return Bill
     */
    Receipt getReceipt(Order order);

    /**
     * Calculate and return bill for args
     * @param args User args
     * @return Bill
     */
    Receipt getReceipt(String[] args);
}
