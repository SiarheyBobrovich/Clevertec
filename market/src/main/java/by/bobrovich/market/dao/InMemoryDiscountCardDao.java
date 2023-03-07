package by.bobrovich.market.dao;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.entity.MarketDiscountCard;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
@ConditionalOnProperty(
        name = "spring.card.database",
        havingValue = "memory"
)
public class InMemoryDiscountCardDao implements DiscountCardDao {

    private final Map<Integer, MarketDiscountCard> discountCardMap;

    public InMemoryDiscountCardDao() {
        discountCardMap = Stream.of(
                new MarketDiscountCard(1234, (byte)10),
                new MarketDiscountCard(2345, (byte)10),
                new MarketDiscountCard(3456, (byte)10)
        ).collect(Collectors.toMap(MarketDiscountCard::getId, x -> x));
    }


    @Override
    public Optional<MarketDiscountCard> getById(Integer id) {
        return Optional.ofNullable(discountCardMap.get(id));
    }
}