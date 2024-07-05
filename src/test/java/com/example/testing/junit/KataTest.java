package com.example.testing.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class KataTest {
    @Nested
    class Fibonacci {
        @Test
        @DisplayName("Can calculate fibonacci sequence")
        void test() {
            var expected = new int[]{ 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };
            var actual = new Kata().fibonacci(10);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class Palindrome {
        @Test
        void test() {
            var kata = new Kata();

            assertThat(kata.isPalindrome("racecar"))
                    .isTrue();
            assertThat(kata.isPalindrome("car"))
                    .isFalse();
            assertThat(kata.isPalindrome("abcdcba"))
                    .isTrue();
        }

        @DisplayName("Can detect palindromes")
        @ParameterizedTest
        @CsvSource({"car, false", "racecar, true", "abcdcba, true"})
        void param_test(String input, boolean expected) {
            var kata = new Kata();

            assertThat(kata.isPalindrome(input))
                    .isEqualTo(expected);
        }
    }
}