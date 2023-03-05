package by.bobrovich.market.cache.algoritm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LRUAlgorithmTest {

    private LRUAlgorithm<Long, String> lruAlgorithm;
    @BeforeEach
    void setUp() {
        lruAlgorithm = new LRUAlgorithm<>(3);
    }


    @Test
    @DisplayName("id=null,object=null")
    void checkPutIgnoreNullNull() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    @DisplayName("id=null,object=null")
    void checkPutIgnoreNullNullTwoElements() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    @DisplayName("id=null, object=null")
    void checkPutIgnoreNullNullThreeElements() {
        lruAlgorithm.put(null, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    @DisplayName("id=null")
    void checkPutIgnoreIdNullOneElement() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    @DisplayName("id=null")
    void checkPutIgnoreIdNullTwoElements() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }
    @Test
    @DisplayName("id=null")
    void checkPutIgnoreIdNullThreeElements() {
        lruAlgorithm.put(null, "Not null");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }
    @Test
    @DisplayName("object=null")
    void checkPutIgnoreObjectNullOneElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }
    @Test
    @DisplayName("object=null")
    void checkPutIgnoreObjectNullTwoElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }
    @Test
    @DisplayName("object=null")
    void checkPutIgnoreObjectNullThreeElements() {
        lruAlgorithm.put(2L, null);
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");
        lruAlgorithm.put(1L, "actual");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkGetMaxSize3() {
        lruAlgorithm.put(1L, "actual");
        lruAlgorithm.put(2L, "two");
        lruAlgorithm.put(3L, "Three");
        lruAlgorithm.put(4L, "Four");

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo(null);
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

        String actual = lruAlgorithm.get(1L);

        assertThat(actual)
                .isEqualTo("actual");
    }

    @Test
    void checkDelete() {
        lruAlgorithm.put(2L, "Two");
        lruAlgorithm.delete(2L);

        String two = lruAlgorithm.get(2L);

        assertThat(two)
                .isEqualTo(null);
    }
}
