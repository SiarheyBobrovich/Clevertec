package by.bobrovich.market.cache.algoritm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LFUAlgorithmTest {

    private LFUAlgorithm<Long, String> lfu;

    @BeforeEach
    void setUp() {
        lfu = new LFUAlgorithm<>(3);
    }

    @Test
    void checkPut4Elements() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");

        Optional<String> actual = lfu.get(1L);

        assertTrue(actual.isEmpty());
    }

    @Test
    void checkPutFIFO() {
        lfu.put(1L, "One");
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(2L, "Two");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");

        Optional<String> actual = lfu.get(1L);

        assertTrue(actual.isEmpty());
    }

    @Test
    void checkPutFIFO2() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(1L, "One");
        lfu.put(1L, "One");
        lfu.put(2L, "Two");

        Optional<String> actual = lfu.get(3L);
        assertTrue(actual.isEmpty());
    }

    @Test
    void checkGetNull() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(1L, "One");
        lfu.put(1L, "One");
        lfu.put(2L, "Two");

        Optional<String> actual = lfu.get(null);
        assertTrue(actual.isEmpty());
    }

    @Test
    void checkGetExistOne() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(1L, "One");
        lfu.put(1L, "One");
        lfu.put(2L, "Two");

        String actual = lfu.get(1L)
                .orElseThrow();

        assertThat(actual)
                .isEqualTo("One");
    }

    @Test
    void checkGetExistTwo() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(1L, "One");
        lfu.put(1L, "One");
        lfu.put(2L, "Two");


        String actual = lfu.get(2L)
                .orElseThrow();

        assertThat(actual)
                .isEqualTo("Two");
    }

    @Test
    void checkGetNotExistFour() {
        lfu.put(2L, "Two");
        lfu.put(1L, "One");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(2L, "Two");
        lfu.put(3L, "Three");
        lfu.put(4L, "Four");
        lfu.put(1L, "One");
        lfu.put(1L, "One");
        lfu.put(2L, "Two");

        String actual = lfu.get(4L)
                .orElseThrow();

        assertThat(actual)
                .isEqualTo("Four");
    }

    @Test
    void checkDelete() {
        lfu.put(2L, "Two");
        lfu.delete(2L);

        assertTrue(lfu.get(2L).isEmpty());
    }
}
