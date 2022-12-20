package by.bobrovich.market.service;

import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.ProductService;
import by.bobrovich.market.api.Receipt;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class MapProductServiceDecorator implements ProductService {
    private final ProductService service;
    private final ConversionService conversionService;

    public MapProductServiceDecorator(ProductService service,
                                      ConversionService conversionService) {
        this.service = service;
        this.conversionService = conversionService;
    }


    @Override
    public Receipt getReceipt(Order order) {
        return service.getReceipt(order);
    }

    @Override
    public Receipt getReceipt(String[] args) {
        return service.getReceipt(args);
    }

    public Receipt getReceipt(MultiValueMap<String, String> parameters) {
        Order order = conversionService.convert(parameters, Order.class);

        return service.getReceipt(order);
    }
}
