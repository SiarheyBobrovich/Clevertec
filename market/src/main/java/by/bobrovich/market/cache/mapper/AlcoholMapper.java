package by.bobrovich.market.cache.mapper;

import by.bobrovich.market.cache.data.request.RequestAlcoholDto;
import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.entity.Alcohol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AlcoholMapper {

    /**
     * Map an alcohol to response dto
     * @param source an alcohol to map
     * @return the response dto
     */
    ResponseAlcoholDto alcoholToResponseAlcoholDto(Alcohol source);

    /**
     * Map the request dto to an alcohol
     * @param source request dto
     * @return an alcohol without ID
     */
    @Mapping(target = "id", ignore = true)
    Alcohol requestAlcoholDtoToAlcohol(RequestAlcoholDto source);
}
