package by.bobrovich.market.service;

import by.bobrovich.market.api.*;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.data.MarketBasket;
import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import by.bobrovich.market.exceptions.ProductQuantityIsNotAvailable;
import by.bobrovich.market.exceptions.ServiceNotAvailableNow;
import by.bobrovich.market.factory.ReceiptFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Semaphore;

@Service
public class MarketService implements ProductService {

    private final OrderConverter<String[], Order> orderConverter;
    private final ProductDao productDao;
    private final DiscountCardDao discountCardDao;
    private final ReceiptFactory receiptFactory;
    private final Semaphore semaphore = new Semaphore(1);

    public MarketService(OrderConverter<String[], Order> orderConverter,
                         ProductDao productDao,
                         DiscountCardDao discountCardDao, ReceiptFactory receiptFactory) {
        this.orderConverter = orderConverter;
        this.productDao = productDao;
        this.discountCardDao = discountCardDao;
        this.receiptFactory = receiptFactory;
    }

    @Override
    public Receipt getReceipt(Order order) {
        final MarketDiscountCard discountCard = getDiscountCardFromDao(order.discountCardNumber());
        final Basket basket = new MarketBasket();
        final List<OrderEntry> orderEntries = order.orderEntries();

        checkAllIdExists(orderEntries.stream()
                .map(OrderEntry::id)
                .toList());

        beginTransaction();
        doTransaction(orderEntries, basket);
        commitTransaction();

        return receiptFactory.create(basket, discountCard, 1234);
    }

    @Override
    public Receipt getReceipt(String[] args) {
        Order order = orderConverter.convert(args);
        assert order != null;

        return getReceipt(order);
    }

    public void updateProduct(MarketProduct product) {
        productDao.update(product);
    }

    private MarketDiscountCard getDiscountCardFromDao(Integer discountCardNumber) {
        return discountCardNumber != null ?
                discountCardDao.getById(discountCardNumber)
                        .orElseThrow(() -> new DiscountCardNotFoundException(discountCardNumber)) :
                null;
    }

    private void beginTransaction() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new ServiceNotAvailableNow("Service is not available now, please try again later");
        }
    }

    private void doTransaction(List<OrderEntry> orderEntries, Basket basket) {
        isAvailableQuantityInDao(orderEntries);

        orderEntries.forEach(p -> {
            Integer productId = p.id();
            Integer orderQuantity = p.quantity();

            MarketProduct product = productDao.getById(productId).orElseThrow();

            basket.addProduct(product, orderQuantity);

            product.setQuantity(product.getQuantity() - orderQuantity);
            updateProduct(product);
        });
    }

    private void commitTransaction() {
        semaphore.release();
    }

    private void checkAllIdExists(List<Integer> id) {
        id.forEach(x -> {
            if (!productDao.exists(x)) {
                throw new ProductNotFoundException(x);
            }
        });
    }

    private void isAvailableQuantityInDao(List<OrderEntry> orderEntries) {
        orderEntries.forEach(x -> {
            if (!productDao.isExistsAndQuantityAvailable(x.id(), x.quantity())) {
                semaphore.release();
                throw new ProductQuantityIsNotAvailable(x.id(), x.quantity());
            }
        });
    }
}