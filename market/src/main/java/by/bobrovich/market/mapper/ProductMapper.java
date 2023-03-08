package by.bobrovich.market.mapper;

import by.bobrovich.market.data.product.request.RequestProductDto;
import by.bobrovich.market.data.product.response.ResponseProductDto;
import by.bobrovich.market.entity.MarketProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    /**
     * Mapping request dto to product
     * @param source current request dto
     * @return product without ID
     */
    @Mapping(target = "id", ignore = true)
    MarketProduct getProduct(RequestProductDto source);

    /**
     * Mapping product to response dto
     * @param source current product
     * @return response dto
     */
    @Mapping(target = "isDiscount", source = "discount")
    ResponseProductDto getResponseDto(MarketProduct source);

}
