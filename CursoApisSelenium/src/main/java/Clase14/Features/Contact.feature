Feature: Contacts in SalesForce

  Scenario: Create a new contact

    Given I have a token and Instance
    And I have a contact
    When I send a request to create a contact
    Then The contact has been created