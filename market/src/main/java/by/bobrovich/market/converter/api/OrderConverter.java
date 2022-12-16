package by.bobrovich.market.converter.api;

public interface OrderConverter<T, E> {
    E convert(T args);
}
