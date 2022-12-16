package by.bobrovich.market.dao;

import by.bobrovich.market.api.Product;
import by.bobrovich.market.api.ProductDao;
import by.bobrovich.market.entity.MarketProduct;
import by.bobrovich.market.exceptions.ProductNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductDao implements ProductDao {
    private final Map<Integer, Product> products;

    public InMemoryProductDao() {
        this.products = new HashMap<>();
        init();
    }

    public InMemoryProductDao(String fileName) throws IOException {
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

    private void init() {
        for (String[] strings : getArgs()) {
            addProduct(strings);
        }
    }

    private void init(String fileName) throws IOException {
        Path path = Paths.get(fileName).toAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        String fileLine;
        while ((fileLine = reader.readLine()) != null) {
            String[] split = fileLine.split(",");
            addProduct(split);
        }
    }

    private void addProduct(String[] args) {
        int id = Integer.parseInt(args[0]);
        MarketProduct product = new MarketProduct(
                id,
                args[1],
                new BigDecimal(args[2]),
                Boolean.parseBoolean(args[3])
        );

        products.put(
                id,
                product
        );
    }

    private String[][] getArgs() {
        return new String[][]{
                {"1", "Loren Ipsum", "1.55", "true"},
                {"2", "Dolor", "2.34", "false"},
                {"3", "Sir amet", "3.32", "false"},
                {"4", "Consectetur adiping", "10.50", "false"},
                {"5", "Elit", "3.12", "false"},
                {"6", "Suspendisse eget", "0.45", "false"},
                {"7", "Placerat massa", "37.34", "false"},
                {"8", "Aenean vulputate", "17.43", "true"},
                {"9", "Quam ac eleifend", "1.50", "false"},
                {"10", "Pharetra", "0.99", "false"},
                {"11", "Integer", "4.23", "false"},
                {"12", "Magna in", "32.45", "false"},
                {"13", "Loren scelerisque", "54.30", "false"},
                {"14", "Efficitur", "2.25", "false"},
                {"15", "Aliquam", "3.50", "false"},
                {"16", "Erat volutpat", "18.50", "false"},
                {"17", "Etian bibendum", "9.75", "false"},
                {"18", "Mauris mauris", "0.65", "false"},
                {"19", "Eget eleifend", "1.75", "false"},
                {"20", "Justo pulvinar", "10.12", "true"},
                {"21", "Quisque", "8.17", "false"},
                {"22", "Ullamcorper", "5.63", "false"},
                {"23", "At velit", "17.80", "false"},
                {"24", "In feugiat", "9.99", "false"},
                {"25", "Aliquet venenatis", "7.55", "false"},
                {"26", "Nisi id", "0.45", "false"},
                {"27", "Malesuada vestibu mi", "10.35", "false"},
                {"28", "Sictum lacus", "53.70", "false"},
                {"29", "Nunc tempor", "0.25", "false"}
        };
    }
}