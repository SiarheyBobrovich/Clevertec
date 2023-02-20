package by.bobrovich.market.dao;

import by.bobrovich.market.entity.MarketDiscountCard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDiscountCardDaoTest {

    private final InMemoryDiscountCardDao discountCardDao = new InMemoryDiscountCardDao();

    @ParameterizedTest
    @ValueSource(ints = {1234,2345,3456})
    void checkGetByIdExists(int id) {
        MarketDiscountCard discountCard = discountCardDao.getById(id)
                .orElseThrow();
        assertEquals(id, discountCard.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void checkGetByIdDoNotExists(int id) {
        assertFalse(discountCardDao.getById(id).isPresent());
    }
}