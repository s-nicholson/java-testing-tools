package com.example.testing.async;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class AsyncService {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final long wait;
    private boolean done = false;

    public AsyncService() {
        this.wait = ThreadLocalRandom.current()
                .nextLong(500L, 10_000L);
        System.out.println("Service will wait %d seconds".formatted(wait / 1000L));
    }

    public void doSomethingAsync() {
        executorService.submit(() -> somethingAsync());
    }

    @SneakyThrows
    private void somethingAsync() {
        Thread.sleep(wait);
        done = true;
    }
}
