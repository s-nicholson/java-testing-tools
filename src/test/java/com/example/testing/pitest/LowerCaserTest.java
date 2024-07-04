package com.example.testing.pitest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * More pitest demo tests showing the drawbacks - trying to catch some mutants
 * is hard work.
 */
class LowerCaserTest {
    private final LowerCaser lowerCaser = new LowerCaser();

    @Test
    void test() {
        var expected = List.of("a", "b", "c");

        var actual = lowerCaser.lowerCase(List.of("A", "B", "C"));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Disabled
    void convoluted_test() {
        List<String> mockList = spy(List.of());

        var actual = lowerCaser.lowerCase(mockList);

        assertThat(actual).isEmpty();
        verify(mockList, never()).iterator();
    }
}