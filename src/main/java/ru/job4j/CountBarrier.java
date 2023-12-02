package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (count < total) {
                monitor.wait();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);
        countBarrier.count();
        countBarrier.count();
        countBarrier.await();
        countBarrier.count();
    }
}