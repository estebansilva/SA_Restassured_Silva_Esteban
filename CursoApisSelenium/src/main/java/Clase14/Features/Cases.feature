Feature: Cases on Salesforce

  Scenario: Create a new case
    Given I have a token and Instance
    And I have a case
    When I send a request to create a new case
    Then A case has been created