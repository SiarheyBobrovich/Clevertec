package by.bobrovich.market.dao;

import by.bobrovich.market.entity.MarketProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductDaoTest {
    private final InMemoryProductDao dao = new InMemoryProductDao();

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    void checkGetByIdExists(int id) {
        dao.getById(id)
                .ifPresent(p -> assertEquals(id, p.getId()));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void checkGetByIdDoNotExists(int id) {
        assertNull(dao.getById(id).orElse(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 15, 25})
    void checkExistsTrue(int id) {
        assertTrue(dao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0})
    void checkExistsFalse(int id) {
        assertFalse(dao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void checkIsExistsAndQuantityAvailableId5True(int quantity) {
        assertTrue(dao.isExistsAndQuantityAvailable(5, quantity));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 50, Integer.MAX_VALUE})
    void checkIsExistsAndQuantityAvailableId5False(int quantity) {
        assertFalse(dao.isExistsAndQuantityAvailable(5, quantity));
    }

    @Test
    void checkUpdateProductQuantityPlus5() {
        int productId = 1;
        MarketProduct product = dao.getById(productId).orElseThrow();
        int quantity =  product.getQuantity() + 5;

        product.setQuantity(quantity);
        dao.update(product);

        dao.getById(productId).ifPresent(p -> assertEquals(quantity, p.getQuantity()));
    }
}