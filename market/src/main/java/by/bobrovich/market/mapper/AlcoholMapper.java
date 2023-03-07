package by.bobrovich.market.mapper;

import by.bobrovich.market.data.alcohol.request.RequestAlcoholDto;
import by.bobrovich.market.data.alcohol.response.ResponseAlcoholDto;
import by.bobrovich.market.entity.Alcohol;
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
