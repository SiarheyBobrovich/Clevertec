package by.bobrovich.market.dao;

import by.bobrovich.market.api.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryProductDaoTest {
    private final InMemoryProductDao dao = new InMemoryProductDao();

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    void getById(int id) {
        Product product = dao.getById(id).orElse(null);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(id, product.getId());
        Assertions.assertNotNull(product.getDescription());
        Assertions.assertNotNull(product.getPrice());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void getByIdFailed(int id) {
        Assertions.assertNull(dao.getById(id).orElse(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 15, 25})
    void exists(int id) {
        Assertions.assertTrue(dao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0})
    void notExists(int id) {
        Assertions.assertFalse(dao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void isQuantityAvailable(int quantity) {
        assertTrue(dao.isExistsAndQuantityAvailable(5, quantity));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 50, Integer.MAX_VALUE})
    void isQuantityAvailableFailed(int quantity) {
        assertFalse(dao.isExistsAndQuantityAvailable(5, quantity));
    }
}