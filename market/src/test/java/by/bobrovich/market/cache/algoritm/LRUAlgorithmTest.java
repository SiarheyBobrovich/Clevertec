package by.bobrovich.market.cache.algoritm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUAlgorithmTest {

    private LRUAlgorithm<Long, String> lruAlgorithm;

    @BeforeEach
    void setUp() {
        lruAlgorithm = new LRUAlgorithm<>(3);
    }

    @Test
    void checkPutIgnoreNullNull() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreNullNullTwoElements() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreNullNullThreeElements() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreIdNullOneElement() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreIdNullTwoElements() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreIdNullThreeElements() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreObjectNullOneElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreObjectNullTwoElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkPutIgnoreObjectNullThreeElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkGetMaxSize3() {
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(2L, "two");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L).orElse("null");

        assertThat(actual)
                .isEqualTo("null");
    }

    @Test
    void checkGetActual() {
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(2L, "two");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.get(1L);
        lruAlgorithm.get(2L);
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.get(1L);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.get(4L);
        lruAlgorithm.get(1L);
        lruAlgorithm.put(2L, "two");
        lruAlgorithm.put(3L, "Three");

        String actual = lruAlgorithm.get(1L).orElseThrow();

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkDelete() {
        lruAlgorithm.put(2L, "Two");
        lruAlgorithm.delete(2L);

        String two = lruAlgorithm.get(2L).orElse("null");

        assertThat(two)
                .isEqualTo("null");
    }
}
