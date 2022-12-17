package by.bobrovich.market.exceptions;

public class ProductQuantityIsNotAvailable extends IllegalStateException {
    public ProductQuantityIsNotAvailable(int id, int quantity) {
        super("Id: " + id + ", quantity: " + quantity + " is not available");
    }
}
