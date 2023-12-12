package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int current;
        int temp;
        do {
            current = get();
            temp = current;
            current += 1;
        } while (!count.compareAndSet(temp, current));

    }

    public int get() {
        return count.get();
    }
}