package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.entity.MarketDiscountCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class JdbcDiscountCardDaoTest {

    private final JdbcDiscountCardDao dao = new JdbcDiscountCardDao();
//    @ParameterizedTest
    @ValueSource(ints = {1234, 2345, 3456})
    void getById(int id) {
        assertDoesNotThrow(() -> {
            MarketDiscountCard card = dao.getById(id).orElse(null);

            assertNotNull(card);
            assertEquals(id, card.getId());
            assertEquals(10, card.getDiscount());
        });
    }
}