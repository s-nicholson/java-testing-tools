package com.example.testing.bdd;

import com.example.testing.bdd.Wallet;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class WalletStepDefinitions {
    private int ids = 1;
    private final Map<String, Wallet> users = new HashMap<>();
    private Exception thrown;

    @Given("{string} has a balance of £{double}")
    public void userHasBalance(String name, double balance) {
        users.put(name, new Wallet(ids++, name, balance));
    }

    @When("{string} tries to pay {string} £{double}")
    public void userTriesToPay(String a, String b, double amount) {
        var userA = users.get(a);
        var userB = users.get(b);
        try {
            userA.pay(userB, amount);
        } catch (Exception e) {
            thrown = e;
        }
    }

    @When("{string} tries to withdraw £{double}")
    public void userTriesToWithdraw(String name, double amount) {
        var user = users.get(name);
        try {
            user.withdraw(amount);
        } catch (Exception e) {
            thrown = e;
        }
    }

    @Then("we get an insufficient funds error")
    public void insufficientFundsError() {
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(Wallet.InsufficientFundsException.class);
    }


    @Then("we get an invalid transaction error")
    public void invalidAmountError() {
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(Wallet.InvalidAmountException.class);
    }

    @Then("{string} will have a balance of £{double}")
    public void userNowHasBalance(String name, double balance) {
        var user = users.get(name);
        assertThat(user.getBalance()).isEqualTo(balance);
    }
}
