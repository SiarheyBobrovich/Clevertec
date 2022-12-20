package by.bobrovich.market.converter.api;

import org.springframework.core.convert.converter.Converter;

public interface OrderConverter<T, E> extends Converter<T, E> {

    E convert(T args);
}
