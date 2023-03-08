package by.bobrovich.market.dao;

import by.bobrovich.market.dao.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.ProductNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
@ConditionalOnProperty(
        name = "spring.product.database",
        havingValue = "memory"
)
public class InMemoryProductDao implements ProductDao {
    private final Map<Integer, MarketProduct> products;
    private final AtomicInteger id = new AtomicInteger(1);
    public InMemoryProductDao() {

        this.products = new HashMap<>(getProductsArray().stream()
                .peek(p -> {
                    if (p.getId() == 0) p.setId(id.getAndIncrement());
                }).collect(Collectors.toMap(MarketProduct::getId, p -> p)));
    }

    @Override
    public Optional<MarketProduct> findById(Integer id) {
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
        if (products.containsKey(product.getId())) {
            products.put(product.getId(), product);
        }else throw new ProductNotFoundException("Product with ID: " + product.getId() + " not found");
    }

    @Override
    public void delete(Integer id) {
        products.remove(id);
    }

    @Override
    public void save(MarketProduct product) {
        int id = this.id.getAndIncrement();
        product.setId(id);
        products.put(id, product);
    }

    @Override
    public List<MarketProduct> findAll() {
        return products.values().stream()
                .toList();
    }

    private List<MarketProduct> getProductsArray() {
        return List.of(
                MarketProduct.builder().description("Loren Ipsum").price(new BigDecimal( "1.55")).quantity(10).isDiscount(true).build(),
                MarketProduct.builder().description("Dolor").price(new BigDecimal( "2.34")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Sir amet").price(new BigDecimal( "3.32")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Consectetur adiping").price(new BigDecimal("10.50")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Elit").price(new BigDecimal( "3.12")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Suspendisse eget").price(new BigDecimal( "0.45")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Placerat massa").price(new BigDecimal( "37.34")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Aenean vulputate").price(new BigDecimal( "17.43")).quantity(10).isDiscount(true).build(),
                MarketProduct.builder().description("Quam ac eleifend").price(new BigDecimal( "1.50")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Pharetra").price(new BigDecimal( "0.99")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Integer").price(new BigDecimal( "4.23")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Magna in").price(new BigDecimal( "32.45")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Loren scelerisque").price(new BigDecimal( "54.30")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Efficitur").price(new BigDecimal( "2.25")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Aliquam").price(new BigDecimal( "3.50")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Erat volutpat").price(new BigDecimal( "18.50")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Etian bibendum").price(new BigDecimal( "9.75")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Mauris mauris").price(new BigDecimal( "0.65")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Eget eleifend").price(new BigDecimal( "1.75")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Justo pulvinar").price(new BigDecimal( "10.12")).quantity(10).isDiscount(true).build(),
                MarketProduct.builder().description("Quisque").price(new BigDecimal( "8.17")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Ullamcorper").price(new BigDecimal( "5.63")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("At velit").price(new BigDecimal( "17.80")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("In feugiat").price(new BigDecimal( "9.99")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Aliquet venenatis").price(new BigDecimal( "7.55")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Nisi id").price(new BigDecimal( "0.45")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Malesuada vestibu mi").price(new BigDecimal( "10.35")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Sictum lacus").price(new BigDecimal( "53.70")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().description("Nunc tempor").price(new BigDecimal("0.25")).quantity(10).isDiscount(false).build(),
                MarketProduct.builder().id( 500).description("Thread Test").price(new BigDecimal("0.25")).quantity(500).isDiscount(false).build());
    }
}