Feature: Leads on Salesforce

  Scenario: Create a new lead

    Given I have a token and Instance
    And I have a lead
    When I send a request to create a lead
    Then A lead has been created

  Scenario: Create a lead without body

    Given I have a token and Instance
    And I have a lead
    When I send a post without body
    Then System show me about the request need a body

  Scenario: Create a lead without course

    Given I have a token and Instance
    And I have a lead without course
    When I send a request with a lead without course
    Then System validate is necessary a course