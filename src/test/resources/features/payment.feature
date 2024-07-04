Feature: paying another user

  Scenario: standard payment
    Given "Alice" has a balance of £10.00
    And "Bob" has a balance of £5.00
    When "Alice" tries to pay "Bob" £10.00
    Then "Alice" will have a balance of £0.00
    And "Bob" will have a balance of £15.00

  Scenario: insufficient funds
    Given "Alice" has a balance of £10.00
    And "Bob" has a balance of £5.00
    When "Alice" tries to pay "Bob" £11.00
    Then we get an insufficient funds error
    And "Alice" will have a balance of £10.00
    And "Bob" will have a balance of £5.00

  Scenario: don't allow negative payments
    Given "Alice" has a balance of £10.00
    And "Bob" has a balance of £5.00
    When "Alice" tries to pay "Bob" £-5.00
    Then we get an invalid transaction error
    And "Alice" will have a balance of £10.00
    And "Bob" will have a balance of £5.00