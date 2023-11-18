package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        boolean allFinished = false;
        Thread[] threads = {first, second};
        while (!allFinished) {
            allFinished = true;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    allFinished = false;
                    break;
                }
            }
        }
        System.out.println(Thread.currentThread().getName());
    }
}
