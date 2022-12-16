package by.bobrovich.market.service;

import by.bobrovich.market.api.*;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.data.MarketBasket;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.factory.ReceiptFactory;

import java.util.List;

public class MarketService implements ProductService {

    private final OrderConverter<String[], Order> orderConverter;
    private final ProductDao productDao;
    private final DiscountCardDao discountCardDao;
    private final ReceiptFactory receiptFactory;

    public MarketService(OrderConverter<String[], Order> orderConverter,
                         ProductDao productDao,
                         DiscountCardDao discountCardDao, ReceiptFactory receiptFactory) {
        this.orderConverter = orderConverter;
        this.productDao = productDao;
        this.discountCardDao = discountCardDao;
        this.receiptFactory = receiptFactory;
    }

    @Override
    public Receipt getBill(Order order) {
        Basket basket = new MarketBasket();
        Integer discountCardNumber = order.discountCardNumber();
        DiscountCard discountCard = discountCardNumber != null ?
                discountCardDao.getById(order.discountCardNumber())
                    .orElseThrow(() -> new DiscountCardNotFoundException(discountCardNumber)) :
                null;

        order.orderEntries().forEach(e -> {
            Integer id = e.id();
            Product product = productDao.getById(id)
                            .orElseThrow(() -> new ProductNotFoundException(id));
                    basket.addProduct(product, e.quantity());
                });

        return receiptFactory.create(basket, discountCard, 1234);
    }

    @Override
    public Receipt getBill(String[] args) {
        Order order = orderConverter.convert(args);
        return getBill(order);
    }
}