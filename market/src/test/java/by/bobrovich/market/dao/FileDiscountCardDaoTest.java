package by.bobrovich.market.dao;

import by.bobrovich.market.entity.MarketDiscountCard;
import by.bobrovich.market.exceptions.DiscountCardNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileDiscountCardDaoTest {

    private final FileDiscountCardDao discountCardDao;

    private FileDiscountCardDaoTest() throws IOException {
        discountCardDao = new FileDiscountCardDao("src/main/resources/in_memory_discount_cards.csv");
    }

    @ParameterizedTest
    @ValueSource(ints = {1234,2345,3456})
    void getById(int id) {
        MarketDiscountCard discountCard = discountCardDao.getById(id).orElse(null);

        assertNotNull(discountCard);
        assertEquals(id, discountCard.getId());
        assertEquals(10, discountCard.getDiscount());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void getByIdFailed(int id) {
        assertThrows(DiscountCardNotFoundException.class,
                () -> discountCardDao.getById(id).orElseThrow(() -> new DiscountCardNotFoundException(id)));
    }
}