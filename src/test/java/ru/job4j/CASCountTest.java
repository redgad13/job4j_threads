package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void increment() {
        CASCount casCount = new CASCount();
        int rsl = casCount.get();
        assertThat(rsl).isEqualTo(0);
        casCount.increment();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        rsl = casCount.get();
        assertThat(rsl).isEqualTo(4);
    }
}