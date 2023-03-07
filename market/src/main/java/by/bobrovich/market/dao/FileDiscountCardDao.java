package by.bobrovich.market.dao;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.entity.MarketDiscountCard;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@ConditionalOnProperty(
        name = "spring.card.database",
        havingValue = "file"
)
public class FileDiscountCardDao implements DiscountCardDao {

    private final Map<Integer, MarketDiscountCard> discountCardMap;
    public FileDiscountCardDao(String fileName) throws IOException {
        final Path path = Paths.get(fileName);
        final List<String> lines = Files.readAllLines(path);

        discountCardMap = lines.stream()
                .filter(row -> row.matches("\\d+,\\d{1,3}"))
                .map(line -> line.split(","))
                .map(strings -> new MarketDiscountCard(
                        Integer.parseInt(strings[0]),
                        Byte.parseByte(strings[1])))
                .collect(Collectors.toMap(MarketDiscountCard::getId, x -> x));
    }

    @Override
    public Optional<MarketDiscountCard> getById(Integer id) {
        return Optional.ofNullable(discountCardMap.get(id));
    }
}