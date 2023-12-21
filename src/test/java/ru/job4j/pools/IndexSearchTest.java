package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexSearchTest {
    @Test
    void whenIntegerLessThan10() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        IndexSearch<Integer> indexSearch = new IndexSearch<>(array, 4, 0, 4);
        int expected = indexSearch.compute();
        assertThat(expected).isEqualTo(3);
    }

    @Test
    void whenStringLessThan10() {
        String[] array = new String[]{"one", "two", "three", "four", "five"};
        IndexSearch<String> indexSearch = new IndexSearch<>(array, "five", 0, 4);
        int expected = indexSearch.compute();
        assertThat(expected).isEqualTo(4);
    }

    @Test
    void whenIntegerMoreThan10() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        IndexSearch<Integer> indexSearch = new IndexSearch<>(array, 11, 0, array.length - 1);
        int expected = indexSearch.compute();
        assertThat(expected).isEqualTo(10);
    }

    @Test
    void whenStringMoreThan10() {
        String[] array = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        IndexSearch<String> indexSearch = new IndexSearch<>(array, "eleven", 0, array.length - 1);
        int expected = indexSearch.compute();
        assertThat(expected).isEqualTo(10);
    }

    @Test
    void whenNoSuchElement() {
        String[] array = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        IndexSearch<String> indexSearch = new IndexSearch<>(array, "rrr", 0, array.length - 1);
        int expected = indexSearch.compute();
        assertThat(expected).isEqualTo(-1);
    }

    @Test
    void whenNoSuchElementViaCommonMethod() {
        String[] array = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        Integer expected = IndexSearch.search(array, "rrr");
        assertThat(expected).isEqualTo(-1);
    }

    @Test
    void whenStringMoreThan10ViaCommonMethod() {
        String[] array = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        Integer expected = IndexSearch.search(array, "eleven");
        assertThat(expected).isEqualTo(10);
    }
}