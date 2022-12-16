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
import java.util.stream.Stream;

public class InMemoryDiscountCardDao implements DiscountCardDao {

    private Map<Integer, DiscountCard> discountCardMap;

    public InMemoryDiscountCardDao() {
        discountCardMap = new HashMap<>();
        init();
    }

    public InMemoryDiscountCardDao(String filename) throws IOException {
        discountCardMap = new HashMap<>();
        init(filename);
    }

    @Override
    public Optional<DiscountCard> getById(Integer id) {
        return Optional.of(discountCardMap.get(id));
    }

    private void init() {
        Stream.of(
          new MarketDiscountCard(1234, (byte)10),
          new MarketDiscountCard(2345, (byte)10),
          new MarketDiscountCard(3456, (byte)10)
        ).forEach(x -> discountCardMap.put(x.getId(), x));
    }

    private void init(String fileName) throws IOException {
        Path path = Paths.get(fileName).toAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        String fileLine;
        while ((fileLine = reader.readLine()) != null) {
            String[] split = fileLine.split(",");
            int id = Integer.parseInt(split[0]);

            discountCardMap.put(
                    id,
                    new MarketDiscountCard(id, Byte.parseByte(split[1]))
            );
        }
    }
}