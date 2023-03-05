package by.bobrovich.market.cache.converter;

import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.entity.Alcohol;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResponseAlcoholDtoToAlcohol implements Converter<ResponseAlcoholDto, Alcohol> {

    @Override
    public Alcohol convert(ResponseAlcoholDto source) {
        return Alcohol.builder()
                .name(source.name())
                .price(source.price())
                .country(source.country())
                .vol(source.vol())
                .build();
    }
}
