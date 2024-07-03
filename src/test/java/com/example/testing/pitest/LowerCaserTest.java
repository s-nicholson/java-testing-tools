package com.example.testing.pitest;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LowerCaserTest {
    private final LowerCaser lowerCaser = new LowerCaser();

    @Test
    void test() {
        var expected = List.of("a", "b", "c");

        var actual = lowerCaser.lowerCase(List.of("A", "B", "C"));

        assertThat(actual).isEqualTo(expected);
    }
}