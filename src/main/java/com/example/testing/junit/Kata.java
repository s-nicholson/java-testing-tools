package com.example.testing.junit;

import java.util.Arrays;
import java.util.List;

public class Kata {
    public int[] fibonacci(int n) {
        int[] a = new int[n];
        a[1] = 1;
        for (int i = 2; i < n; i++)
            a[i] = a[i-1] + a[i-2];
        return a;
    }

    public boolean isPalindrome(String maybePalindrome) {
        var reversed = new StringBuilder(maybePalindrome)
                .reverse()
                .toString();
        return maybePalindrome.equals(reversed);
    }
}
