package by.bobrovich.market.dao;

import by.bobrovich.market.api.Product;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileProductDao implements ProductDao {
    private final Map<Integer, MarketProduct> products;

    public FileProductDao(String fileName) throws IOException {
        this.products = new HashMap<>();
        init(fileName);
    }

    @Override
    public Optional<Product> getById(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public boolean exists(Integer id) {
        return products.containsKey(id);
    }

    private void init(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        String fileLine;
        while ((fileLine = reader.readLine()) != null) {
            if (!fileLine.startsWith("ID")) {
                String[] args = fileLine.split(",");
                addProduct(args);
            }
        }
    }

    private void addProduct(String[] args) {
        int id = Integer.parseInt(args[0]);
        String description = args[1];
        BigDecimal price = new BigDecimal(args[2]);
        int quantity = Integer.parseInt(args[3]);
        boolean isDiscount = Boolean.parseBoolean(args[4]);

        products.put(
                id,
                new MarketProduct(
                        id,
                        description,
                        price,
                        quantity,
                        isDiscount
                )
        );
    }
}