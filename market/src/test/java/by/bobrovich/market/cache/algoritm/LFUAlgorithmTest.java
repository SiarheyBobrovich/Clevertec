package by.bobrovich.market.cache.algoritm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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

        String one = lfu.get(1L);

        assertThat(one)
                .isEqualTo(null);
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

        String one = lfu.get(1L);

        assertThat(one)
                .isEqualTo(null);
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

        String three = lfu.get(3L);

        assertThat(three)
                .isEqualTo(null);
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

        String actual = lfu.get(null);

        assertThat(actual)
                .isEqualTo(null);
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

        String one = lfu.get(1L);

        assertThat(one)
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

        String two = lfu.get(2L);

        assertThat(two)
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

        String four = lfu.get(4L);

        assertThat(four)
                .isEqualTo("Four");
    }

    @Test
    void checkDelete() {
        lfu.put(2L, "Two");
        lfu.delete(2L);

        String two = lfu.get(2L);

        assertThat(two)
                .isEqualTo(null);
    }
}