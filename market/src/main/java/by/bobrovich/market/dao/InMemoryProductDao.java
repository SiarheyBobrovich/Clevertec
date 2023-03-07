package by.bobrovich.market.dao;

import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@ConditionalOnProperty(
        name = "spring.product.database",
        havingValue = "file"
)
public class InMemoryProductDao implements ProductDao {
    protected final Map<Integer, MarketProduct> products;

    public InMemoryProductDao() {
        MarketProduct[] productsArray = getProductsArray();
        this.products = Arrays.stream(productsArray)
                .collect(Collectors.toMap(MarketProduct::getId, x -> x));
    }

    @Override
    public Optional<MarketProduct> getById(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public boolean exists(Integer id) {
        return products.containsKey(id);
    }

    @Override
    public boolean isExistsAndQuantityAvailable(Integer id, Integer quantity) {
        return quantity >= 0 &&
                products.containsKey(id) &&
                products.get(id).getQuantity() >= quantity;
    }

    @Override
    public void update(MarketProduct product) {
        products.put(product.getId(), product);
    }

    private MarketProduct[] getProductsArray() {
        return new MarketProduct[]{
                new MarketProduct(1, "Loren Ipsum", new BigDecimal( "1.55"), 10, true),
                new MarketProduct(2, "Dolor", new BigDecimal( "2.34"), 10, false),
                new MarketProduct(3, "Sir amet", new BigDecimal( "3.32"), 10, false),
                new MarketProduct(4, "Consectetur adiping", new BigDecimal("10.50"), 10, false),
                new MarketProduct(5, "Elit", new BigDecimal( "3.12"), 10, false),
                new MarketProduct(6, "Suspendisse eget", new BigDecimal( "0.45"), 10, false),
                new MarketProduct(7, "Placerat massa", new BigDecimal( "37.34"), 10, false),
                new MarketProduct(8, "Aenean vulputate", new BigDecimal( "17.43"), 10, true),
                new MarketProduct(9, "Quam ac eleifend", new BigDecimal( "1.50"), 10, false),
                new MarketProduct(10, "Pharetra", new BigDecimal( "0.99"), 10, false),
                new MarketProduct(11, "Integer", new BigDecimal( "4.23"), 10, false),
                new MarketProduct(12, "Magna in", new BigDecimal( "32.45"), 10, false),
                new MarketProduct(13, "Loren scelerisque", new BigDecimal( "54.30"), 10, false),
                new MarketProduct(14, "Efficitur", new BigDecimal( "2.25"), 10, false),
                new MarketProduct(15, "Aliquam", new BigDecimal( "3.50"), 10, false),
                new MarketProduct(16, "Erat volutpat", new BigDecimal( "18.50"), 10, false),
                new MarketProduct(17, "Etian bibendum", new BigDecimal( "9.75"), 10, false),
                new MarketProduct(18, "Mauris mauris", new BigDecimal( "0.65"), 10, false),
                new MarketProduct(19, "Eget eleifend", new BigDecimal( "1.75"), 10, false),
                new MarketProduct(20, "Justo pulvinar", new BigDecimal( "10.12"), 10, true),
                new MarketProduct(21, "Quisque", new BigDecimal( "8.17"), 10, false),
                new MarketProduct(22, "Ullamcorper", new BigDecimal( "5.63"), 10, false),
                new MarketProduct(23, "At velit", new BigDecimal( "17.80"), 10, false),
                new MarketProduct(24, "In feugiat", new BigDecimal( "9.99"), 10, false),
                new MarketProduct(25, "Aliquet venenatis", new BigDecimal( "7.55"), 10, false),
                new MarketProduct(26, "Nisi id", new BigDecimal( "0.45"), 10, false),
                new MarketProduct(27, "Malesuada vestibu mi", new BigDecimal( "10.35"), 10, false),
                new MarketProduct(28, "Sictum lacus", new BigDecimal( "53.70"), 10, false),
                new MarketProduct(29, "Nunc tempor", new BigDecimal("0.25"), 10, false),
                new MarketProduct(500, "Thread Test", new BigDecimal("0.25"), 500, false)
        };
    }
}