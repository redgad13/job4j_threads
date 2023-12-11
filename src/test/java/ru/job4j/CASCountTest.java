package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void incrementWithOneThread() {
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

    @Test
    void incrementWithSeveralThreads() throws InterruptedException {
        CASCount casCount = new CASCount();
        int rsl = casCount.get();
        assertThat(rsl).isEqualTo(0);
        Thread first = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                casCount.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                casCount.increment();
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        rsl = casCount.get();
        assertThat(rsl).isEqualTo(200);
    }
}