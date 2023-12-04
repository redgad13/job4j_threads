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

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);
        Thread first = new Thread(countBarrier::count);
        Thread second = new Thread(countBarrier::count);
        Thread third = new Thread(countBarrier::count);
        Thread fourth = new Thread(countBarrier::count);
        Thread fifth = new Thread(countBarrier::await);
        first.start();
        second.start();
        third.start();
        fourth.start();
        fifth.start();
    }
}