package by.bobrovich.market.dao.postgresql;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDao2Test {

    private final JdbcProductDao2 dao = new JdbcProductDao2();

    @Test
    void existsAllId() {
        assertTrue(dao.existsAllId(List.of(1, 2, 3, 4, 5, 6, 7, 8)));
        assertTrue(dao.existsAllId(List.of(1)));
        assertFalse(dao.existsAllId(List.of()));
        assertFalse(dao.existsAllId(null));
        assertFalse(dao.existsAllId(List.of(1, 2, 3, 4, 5, 6, 7, Integer.MIN_VALUE)));
    }
}