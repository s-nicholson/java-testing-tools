Feature: users can withdraw funds

  Scenario: standard withdrawl
    Given "Alice" has a balance of £10.00
    When "Alice" tries to withdraw £10.00
    Then "Alice" will have a balance of £0.00

  Scenario: insufficient funds
    Given "Alice" has a balance of £10.00
    When "Alice" tries to withdraw £11.00
    Then we get an insufficient funds error
    And "Alice" will have a balance of £10.00

  Scenario: don't allow negative payments
    Given "Alice" has a balance of £10.00
    When "Alice" tries to withdraw £-5.00
    Then we get an invalid transaction error
    And "Alice" will have a balance of £10.00