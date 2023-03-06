package by.bobrovich.market.cache.converter;

import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.entity.Alcohol;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlcoholToResponseAlcoholDto implements Converter<Alcohol, ResponseAlcoholDto> {

    @Override
    public ResponseAlcoholDto convert(Alcohol source) {
        return ResponseAlcoholDto.builder()
                .name(source.getName())
                .country(source.getCountry())
                .vol(source.getVol())
                .price(source.getPrice())
                .build();
    }
}
