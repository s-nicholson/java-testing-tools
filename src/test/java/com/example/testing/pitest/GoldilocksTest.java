package com.example.testing.pitest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * A test which demonstrates the value of pitest - these tests don't catch a mutation.
 */
class GoldilocksTest {
    private final Goldilocks goldilocks = new Goldilocks();

    @Test
    void too_low() {
        assertThat(goldilocks.likesPorridge(30))
                .isFalse();
    }

    @Test
    void too_high() {
        assertThat(goldilocks.likesPorridge(90))
                .isFalse();
    }

    @Test
    void just_right() {
        assertThat(goldilocks.likesPorridge(50))
                .isFalse();
    }
}