package by.bobrovich.market.dao;

import by.bobrovich.market.dao.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@ConditionalOnProperty(
        name = "spring.product.database",
        havingValue = "file"
)
public class FileProductDao implements ProductDao {
    private final Map<Integer, MarketProduct> products;

    public FileProductDao(@Value("${spring.product.filename}") String fileName) throws IOException {
        final Path path = Paths.get(fileName);
        final List<String> lines = Files.readAllLines(path);

        products = lines.stream()
                .filter(fileLine -> !fileLine.startsWith("ID"))
                .map(line -> line.split(","))
                .map(strings -> new MarketProduct(
                        Integer.parseInt(strings[0]),
                        strings[1],
                        new BigDecimal(strings[2]),
                        Integer.parseInt(strings[3]),
                        Boolean.parseBoolean(strings[4])))
                .collect(Collectors.toMap(MarketProduct::getId, x -> x));
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
        return quantity >= 0 && products.containsKey(id) && products.get(id).getQuantity() >= quantity;
    }

    @Override
    public void update(MarketProduct product) {
        products.put(product.getId(), product);
    }

    @Override
    public void delete(Integer id) {
        products.remove(id);
    }

    @Override
    public void save(MarketProduct product) {
        products.put(product.getId(), product);
    }

    @Override
    public List<MarketProduct> findAll() {
        return products.values().stream()
                .toList();
    }
}