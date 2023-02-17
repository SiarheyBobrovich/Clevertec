package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.entity.MarketDiscountCard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class JdbcDiscountCardDaoTest {

    private JdbcDiscountCardDao dao;
    @ParameterizedTest
    @ValueSource(ints = {1234, 2345, 3456})
    void getById(int id) {
        new StandardEnvironment()
                .setRequiredProperties();
        assertDoesNotThrow(() -> {
            MarketDiscountCard card = dao.getById(id).orElse(null);

            assertNotNull(card);
            assertEquals(id, card.getId());
            assertEquals(10, card.getDiscount());
        });
    }
}