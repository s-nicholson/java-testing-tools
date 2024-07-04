package com.example.testing.bdd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;

/**
 * Super simple financial transaction type class.
 * In no way is this the right way to do this in the real world.
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Wallet {
    private final int id;
    private final String name;

    private double balance = 0.0;

    public void pay(Wallet payee, double amount) {
        validateAmount(amount);

        this.balance -= amount;
        payee.balance += amount;
    }

    public void withdraw(double amount) {
        validateAmount(amount);

        this.balance -= amount;
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "%f is not a valid amount for a payment".formatted(amount));
        }
        if ((this.balance - amount) < 0.0) {
            throw new InsufficientFundsException(
                    "%s has insufficient funds".formatted(this.name));
        }
    }

    @StandardException
    public static class InsufficientFundsException extends RuntimeException {
    }

    @StandardException
    public static class InvalidAmountException extends RuntimeException {
    }
}
