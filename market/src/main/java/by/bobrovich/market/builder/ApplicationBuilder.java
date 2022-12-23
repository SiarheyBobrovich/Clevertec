package by.bobrovich.market.builder;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.api.Order;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.api.ProductService;
import by.bobrovich.market.converter.ArgsOrderConverter;
import by.bobrovich.market.converter.api.OrderConverter;
import by.bobrovich.market.dao.FileDiscountCardDao;
import by.bobrovich.market.dao.FileProductDao;
import by.bobrovich.market.dao.InMemoryDiscountCardDao;
import by.bobrovich.market.dao.InMemoryProductDao;
import by.bobrovich.market.factory.ReceiptFactory;
import by.bobrovich.market.service.MarketService;

import java.io.IOException;

public class ApplicationBuilder {
    private ProductDao productDao;
    private DiscountCardDao discountCardDao;
    private ReceiptFactory receiptFactory;
    private OrderConverter<String[], Order> converter;

    public ProductDao getProductDao() {
        return productDao;
    }

    public ApplicationBuilder productDao(ProductDao productDao) {
        this.productDao = productDao;
        return this;
    }

    public ApplicationBuilder productDao(String arg) {
        if (arg != null && arg.startsWith("products")) {
            String filename;
            try {
                filename = getFilename(arg);
                this.productDao = new FileProductDao(filename);
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return this;
    }

    public DiscountCardDao getDiscountCardDao() {
        return discountCardDao;
    }

    public ApplicationBuilder discountCardDao(String arg) {
        if (arg != null && arg.startsWith("cards")) {
            String filename;
            try {
                filename = getFilename(arg);
                this.discountCardDao = new FileDiscountCardDao(filename);
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return this;
    }

    public ApplicationBuilder discountCardDao(DiscountCardDao discountCardDao) {
        this.discountCardDao = discountCardDao;
        return this;
    }

    public ApplicationBuilder receiptFactory(ReceiptFactory receiptFactory) {
        this.receiptFactory = receiptFactory;
        return this;
    }

    public OrderConverter<String[], Order> getConverter() {
        return converter;
    }

    public ApplicationBuilder converter(OrderConverter<String[], Order> converter) {
        this.converter = converter;
        return this;
    }

    public ProductService build() {
        return new MarketService(
                converter == null ? new ArgsOrderConverter() : converter,
                productDao == null ? new InMemoryProductDao() : productDao,
                discountCardDao == null ? new InMemoryDiscountCardDao() : discountCardDao,
                receiptFactory == null ? new ReceiptFactory() : receiptFactory
        );
    }

    private String getFilename(String arg) throws IOException {
        int index = arg.indexOf("-");

        if (index == -1) {
            throw new IOException("Args must contains '-'");
        }

        return arg.substring(index + 1);
    }
}