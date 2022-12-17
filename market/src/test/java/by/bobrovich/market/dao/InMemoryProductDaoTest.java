package by.bobrovich.market.dao;

import by.bobrovich.market.api.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}