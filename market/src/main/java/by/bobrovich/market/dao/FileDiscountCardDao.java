package by.bobrovich.market.dao;

import by.bobrovich.market.api.DiscountCard;
import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.entity.MarketDiscountCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileDiscountCardDao implements DiscountCardDao {

    private final Map<Integer, MarketDiscountCard> discountCardMap;
    public FileDiscountCardDao(String filename) throws IOException {
        discountCardMap = new HashMap<>();
        init(filename);
    }

    @Override
    public Optional<DiscountCard> getById(Integer id) {
        return Optional.ofNullable(discountCardMap.get(id));
    }

    private void init(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        String fileLine;
        while ((fileLine = reader.readLine()) != null) {
            addCard(fileLine);
        }
    }

    private void addCard(String row) {
        if (!row.startsWith("ID")) {
            String[] split = row.split(",");

            int id = Integer.parseInt(split[0]);
            byte discount = Byte.parseByte(split[1]);

            discountCardMap.put(
                    id,
                    new MarketDiscountCard(id, discount)
            );
        }
    }
}