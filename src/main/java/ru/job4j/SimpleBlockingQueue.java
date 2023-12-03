package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int size;

    public synchronized void offer(T value) throws InterruptedException {
        while (size > queue.size()) {
            this.wait();
        }
        queue.add(value);
        size++;
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T rsl;
        while (queue.isEmpty()) {
            this.wait();
        }
        rsl = queue.poll();
        notifyAll();
        return rsl;
    }
}