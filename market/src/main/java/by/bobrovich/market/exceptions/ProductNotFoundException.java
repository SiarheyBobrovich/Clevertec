package by.bobrovich.market.exceptions;

public class ProductNotFoundException extends IllegalStateException {
    public ProductNotFoundException(String s) {
        super(s);
    }

    public ProductNotFoundException(int id) {
        super("Product id: " + id + " not found");
    }
}
