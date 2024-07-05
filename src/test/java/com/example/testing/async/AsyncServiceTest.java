package com.example.testing.async;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsyncServiceTest {
    private static final AsyncService asyncService = new AsyncService();

    @Nested
    class WithoutAwaitility {
        /**
         * This test is flakey because it asserts on the result of some async
         * processing, sometimes it might pass and other times it will fail.
         */
        @Disabled
        @Test
        void async_thing_is_done() {
            asyncService.doSomethingAsync();

            assertTrue(asyncService.isDone());
        }

        /**
         * This test is less likely to flake because we've introduced a fixed sleep.
         * Fine here because we *know* the maximum wait of the async code, but in
         * the real world this won't always be possible.
         * <p>
         * Also, it can add a lot of unnecessary waiting to your tests.
         */
        @Test
        @SneakyThrows
        void async_thing_is_done_wait() {
            asyncService.doSomethingAsync();

            Thread.sleep(10_000L);

            assertTrue(asyncService.isDone());
        }
    }

    @Nested
    class WithAwaitility {
        /**
         * Awaitility gives us a flexible way of asserting on our async code.
         * We could write something like this ourselves with a loop and sleeps, but
         * this is much cleaner.
         */
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