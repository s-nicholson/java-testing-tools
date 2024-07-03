package com.example.testing.async;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class AsyncServiceTest {
    private static final AsyncService asyncService = new AsyncService();

    @Nested
    class WithoutAwaitility {
        @Test
        @SneakyThrows
        void async_thing_is_done() {
            asyncService.doSomethingAsync();

            Thread.sleep(10_000L);

            assertTrue(asyncService.isDone());
        }
    }

    @Nested
    class WithAwaitility {
        @Test
        void async_thing_is_done() {
            asyncService.doSomethingAsync();

            await().pollInSameThread()
                    .atMost(Duration.ofSeconds(10L))
                    .pollInterval(Duration.ofSeconds(1L))
                    .pollDelay(Duration.ofSeconds(1L))
                    .ignoreExceptions()
                    .untilAsserted(() -> {
                        var done = asyncService.isDone();
                        System.out.println("Is done: %b".formatted(done));
                        assertTrue(done);
                    });
        }
    }
}