package by.bobrovich.market.dao;

import by.bobrovich.market.api.DiscountCardDao;
import by.bobrovich.market.entity.MarketDiscountCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryDiscountCardDao implements DiscountCardDao {

    private final Map<Integer, MarketDiscountCard> discountCardMap;

    public InMemoryDiscountCardDao() {
        discountCardMap = new HashMap<>();
        init();
    }


    @Override
    public Optional<MarketDiscountCard> getById(Integer id) {
        return Optional.ofNullable(discountCardMap.get(id));
    }

    private void init() {
        Stream.of(
          new MarketDiscountCard(1234, (byte)10),
          new MarketDiscountCard(2345, (byte)10),
          new MarketDiscountCard(3456, (byte)10)
        ).forEach(x -> discountCardMap.put(x.getId(), x));
    }
}