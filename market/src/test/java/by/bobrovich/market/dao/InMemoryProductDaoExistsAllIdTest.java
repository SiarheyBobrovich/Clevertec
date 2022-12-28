package by.bobrovich.market.dao;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductDaoExistsAllIdTest {

    private final InMemoryProductDaoExistsAllId dao = new InMemoryProductDaoExistsAllId();
    @Test
    void existsAllId() {
        assertTrue(dao.existsAllId(List.of(1,2,3,4,5,6,7,8, 10, 11, 12)));
        assertTrue(dao.existsAllId(List.of(5, 10, 15, 20, 25, 28)));
        assertFalse(dao.existsAllId(List.of()));
        assertFalse(dao.existsAllId(null));
    }
}