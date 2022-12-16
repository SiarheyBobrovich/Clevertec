package by.bobrovich.market.dao;

import by.bobrovich.market.api.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InMemoryProductDaoTest {
    private static final InMemoryProductDao dao = new InMemoryProductDao();

    @Test
    void initFromFile() {
        Assertions.assertDoesNotThrow(() -> new InMemoryProductDao("src/main/resources/in_memory_products.csv"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20})
    void getById(int id) {
        Product product = dao.getById(id).get();
        Assertions.assertEquals(id, product.getId());
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