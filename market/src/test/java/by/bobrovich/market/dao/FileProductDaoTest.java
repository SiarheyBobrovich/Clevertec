package by.bobrovich.market.dao;

import by.bobrovich.market.entity.MarketProduct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileProductDaoTest {

    private final FileProductDao productDao;
    private FileProductDaoTest() throws IOException {
        productDao = new FileProductDao("src/main/resources/in_memory_products.csv");
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 29})
    void getById(int id) {
        MarketProduct product = productDao.getById(id).orElse(null);

        assertNotNull(product);
        assertEquals(id, product.getId());
        assertNotNull(product.getDescription());
        assertNotNull(product.getPrice());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, 30})
    void getByIdFailed(int id) {
        MarketProduct product = productDao.getById(id).orElse(null);

        assertNull(product);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 15, 25, 28})
    void exists(int id) {
        assertTrue(productDao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void existsFailed(int id) {
        assertFalse(productDao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void isQuantityAvailable(int quantity) {
        assertTrue(productDao.isExistsAndQuantityAvailable(5, quantity));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 50, Integer.MAX_VALUE})
    void isQuantityAvailableFailed(int quantity) {
        assertFalse(productDao.isExistsAndQuantityAvailable(5, quantity));
    }
}