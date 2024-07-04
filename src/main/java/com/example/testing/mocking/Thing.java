package com.example.testing.mocking;

import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;

/**
 * Simple class with some constructor injected dependencies to demonstrate the benefit of isolation using mocking.
 */
@RequiredArgsConstructor
public class Thing {
    private final Checker checker;
    private final Actor actor;

    public void doSomething(String...args) throws InvalidArgsException {
        if (!checker.check(args)) {
            throw new InvalidArgsException(
                    "Invalid args: %s".formatted(String.join(", ",  args)));
        }
        for (String s : args) {
            actor.act(s);
        }
    }

    @StandardException
    public static class InvalidArgsException extends Exception {}
}
