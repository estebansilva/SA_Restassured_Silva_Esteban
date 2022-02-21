Feature: Create a account

  Scenario: Create a new account
    Given I got the access token and instance url
    And I got a new account
    When I send a request to create an account
    Then an account has been created