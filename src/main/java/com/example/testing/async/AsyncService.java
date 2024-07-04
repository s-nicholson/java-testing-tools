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
        // Make the wait fixed for an instance of this class, that way we can see the benefit of awaitility
        this.wait = ThreadLocalRandom.current()
                .nextLong(500L, 10_000L);
        System.out.printf("Service will wait %d seconds%n", wait / 1000L);
    }

    public void doSomethingAsync() {
        executorService.submit(this::somethingAsync);
    }

    @SneakyThrows
    private void somethingAsync() {
        // Runnable body pulled out to a separate method so we can sneakythrows
        Thread.sleep(wait);
        done = true;
    }
}
