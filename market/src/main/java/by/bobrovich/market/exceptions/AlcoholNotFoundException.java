package by.bobrovich.market.exceptions;

public class AlcoholNotFoundException extends IllegalStateException {
    public AlcoholNotFoundException() {
    }

    public AlcoholNotFoundException(String s) {
        super(s);
    }
}
