package com.example.testing.mocking;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ThingTest {
    @Nested
    class WithoutMockito {
        @Test
        @SneakyThrows
        void valid_args_are_acted_upon() {
            var actedOn = new ArrayList<String>();
            var thing = new Thing(
                    args -> true,
                    actedOn::add
            );

            thing.doSomething("a", "b", "c");

            // all our values were passed to Actor
            assertThat(actedOn).contains("a", "b", "c");
        }

        @Test
        void invalid_args_throws_exception() {
            var actedOn = new ArrayList<String>();
            var thing = new Thing(
                    args -> false,
                    actedOn::add
            );

            var thrown = assertThrows(Thing.InvalidArgsException.class,
                    () -> thing.doSomething("a", "b", "c"));
            assertThat(thrown.getMessage()).contains("a, b, c");

            assertThat(actedOn).isEmpty();
        }
    }

    @Nested
    class WithMockito {
        @Test
        @SneakyThrows
        void valid_args_are_acted_upon() {
            var mockChecker = mock(Checker.class);
            when(mockChecker.check(any())).thenReturn(true);
            var mockActor = mock(Actor.class);
            var thing = new Thing(mockChecker, mockActor);

            thing.doSomething("a", "b", "c");

            verify(mockActor).act("a");
            verify(mockActor).act("b");
            verify(mockActor).act("c");
        }

        @Test
        @SneakyThrows
        void valid_args_are_acted_upon_with_captor() {
            var mockChecker = mock(Checker.class);
            when(mockChecker.check(any())).thenReturn(true);
            var mockActor = mock(Actor.class);
            var thing = new Thing(mockChecker, mockActor);

            thing.doSomething("a", "b", "c");

            var captor = ArgumentCaptor.forClass(String.class);
            verify(mockActor, times(3)).act(captor.capture());
            assertThat(captor.getAllValues()).containsExactly("a", "b", "c");
        }

        @Test
        void invalid_args_throws_exception() {
            var mockChecker = mock(Checker.class);
            when(mockChecker.check(any())).thenReturn(false);
            var mockActor = mock(Actor.class);
            var thing = new Thing(mockChecker, mockActor);

            var thrown = assertThrows(Thing.InvalidArgsException.class,
                    () -> thing.doSomething("a", "b", "c"));
            assertThat(thrown.getMessage()).contains("a, b, c");
            verifyNoInteractions(mockActor);
        }
    }
}
