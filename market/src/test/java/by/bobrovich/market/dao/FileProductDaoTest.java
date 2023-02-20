package by.bobrovich.market.dao;

import by.bobrovich.market.entity.MarketProduct;
import org.junit.jupiter.api.DisplayName;
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
    void checkGetByIdExists(int id) {
        MarketProduct product = productDao.getById(id).orElseThrow();
        assertEquals(id, product.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, 30})
    void checkGetByIdDoesNotExists(int id) {
        assertFalse(productDao.getById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 15, 25, 28})
    void checkExistsTrue(int id) {
        assertTrue(productDao.exists(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
    void checkExistsFalse(int id) {
        assertFalse(productDao.exists(id));
    }

    @DisplayName("Id=true,Qty=true")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void checkIsExistsAndQuantityAvailableIdTrueQtyTrue(int quantity) {
        int id = 10;
        assertTrue(productDao.isExistsAndQuantityAvailable(id, quantity));
    }
    @DisplayName("Id=false,Qty=true")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void checkIsExistsAndQuantityAvailableIdFalseQtyTrue(int quantity) {
        int id = Integer.MAX_VALUE;
        assertFalse(productDao.isExistsAndQuantityAvailable(id, quantity));
    }

    @DisplayName("Id=true,Qty=false")
    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 50, Integer.MAX_VALUE})
    void checkIsExistsAndQuantityAvailableIdTrueQtyFalse(int quantity) {
        int id = 10;
        assertFalse(productDao.isExistsAndQuantityAvailable(id, quantity));
    }
    @DisplayName("Id=false,Qty=false")
    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 50, Integer.MAX_VALUE})
    void checkIsExistsAndQuantityAvailableIdFalseQtyFalse(int quantity) {
        int id = 0;
        assertFalse(productDao.isExistsAndQuantityAvailable(id, quantity));
    }
}