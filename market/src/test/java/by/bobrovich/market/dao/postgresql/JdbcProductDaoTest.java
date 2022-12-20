package by.bobrovich.market.dao.postgresql;

import by.bobrovich.market.entity.MarketProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDaoTest {
    private final JdbcProductDao dao = new JdbcProductDao();
//    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20})
    void getById(int id) {
        MarketProduct product = dao.getById(id).orElse(null);

        assertNotNull(product);
        assertEquals(id, product.getId());
        assertNotNull(product.getDescription());
        assertNotNull(product.getPrice());
    }

//    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20})
    void exists(int id) {
        assertTrue(dao.exists(id));
    }

//    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void existsFailed(int id) {
        assertFalse(dao.exists(id));
    }

//    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20})
    void isExistsAndQuantityAvailable(int id) {
        assertTrue(dao.isExistsAndQuantityAvailable(id, 1));
        assertTrue(dao.isExistsAndQuantityAvailable(id, 5));
        assertTrue(dao.isExistsAndQuantityAvailable(id, 10));
    }

//    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 20})
    void isExistsAndQuantityAvailableFailed(int id) {
        assertFalse(dao.isExistsAndQuantityAvailable(id, 11));
        assertFalse(dao.isExistsAndQuantityAvailable(id, 15));
        assertFalse(dao.isExistsAndQuantityAvailable(id, 25));
    }

//    @Test
    void update() {
        MarketProduct product = dao.getById(1).orElse(null);

        assertNotNull(product);

        int quantity = product.getQuantity();
        product.setQuantity(0);

        assertDoesNotThrow(() -> dao.update(product));
        assertFalse(dao.isExistsAndQuantityAvailable(1, 1));

        MarketProduct updatedProduct = dao.getById(1).orElse(null);

        assertNotNull(updatedProduct);
        assertEquals(product, updatedProduct);

        updatedProduct.setQuantity(quantity);
        assertDoesNotThrow(() -> dao.update(updatedProduct));

        assertTrue(dao.isExistsAndQuantityAvailable(1, quantity));
    }
}