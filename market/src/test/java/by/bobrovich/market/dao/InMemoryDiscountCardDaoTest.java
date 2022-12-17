package by.bobrovich.market.dao;

import by.bobrovich.market.api.DiscountCard;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDiscountCardDaoTest {

    private final InMemoryDiscountCardDao discountCardDao = new InMemoryDiscountCardDao();

    @ParameterizedTest
    @ValueSource(ints = {1234,2345,3456})
    void getById(int id) {
        DiscountCard discountCard = discountCardDao.getById(id).orElse(null);

        assertNotNull(discountCard);
        assertEquals(id, discountCard.getId());
        assertEquals(10, discountCard.getDiscount());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void getByIdFailed(int id) {
        assertThrows(DiscountCardNotFoundException.class,
                () -> discountCardDao.getById(id)
                        .orElseThrow(() -> new DiscountCardNotFoundException(id)));
    }
}