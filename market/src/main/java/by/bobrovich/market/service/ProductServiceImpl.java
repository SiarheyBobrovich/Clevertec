package by.bobrovich.market.service;

import by.bobrovich.market.dao.api.ProductDao;
import by.bobrovich.market.data.product.request.RequestProductDto;
import by.bobrovich.market.data.product.response.ResponseProductDto;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.mapper.ProductMapper;
import by.bobrovich.market.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ResponseProductDto getById(Integer id) {
        Optional<MarketProduct> optionalProduct = productDao.findById(id);
        MarketProduct product = optionalProduct.orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with id: %d not found", id)));
        return mapper.getResponseDto(product);
    }

    @Override
    public List<ResponseProductDto> getAll() {
        return productDao.findAll().stream()
                .map(mapper::getResponseDto)
                .toList();
    }

    @Override
    public void save(RequestProductDto dto) {
        MarketProduct product = mapper.getProduct(dto);
        productDao.save(product);
    }

    @Override
    public void update(Integer id, RequestProductDto dto) {
        MarketProduct product = mapper.getProduct(dto);
        product.setId(id);
        productDao.update(product);
    }

    @Override
    public void delete(Integer id) {
        productDao.delete(id);
    }
}
