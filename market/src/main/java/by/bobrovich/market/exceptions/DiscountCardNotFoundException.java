package by.bobrovich.market.exceptions;

public class DiscountCardNotFoundException extends IllegalStateException {
    public DiscountCardNotFoundException(int discountCardNumber) {
        super("Card: " + discountCardNumber + " not found");
    }
}
