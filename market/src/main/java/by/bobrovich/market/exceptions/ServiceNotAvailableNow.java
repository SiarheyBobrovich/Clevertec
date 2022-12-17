package by.bobrovich.market.exceptions;

public class ServiceNotAvailableNow extends RuntimeException {
    public ServiceNotAvailableNow(String e) {
        super(e);
    }
}
