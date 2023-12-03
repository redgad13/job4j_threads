package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferIsOK() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>();
        sbq.offer(1);
        sbq.offer(2);
        sbq.offer(3);
        assertThat(sbq.poll()).isEqualTo(1);
        assertThat(sbq.poll()).isEqualTo(2);
        assertThat(sbq.poll()).isEqualTo(3);
    }
}