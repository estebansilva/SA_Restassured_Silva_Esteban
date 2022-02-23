Feature: Campaign in Salesforce

  Scenario Outline: Create a campaign in salesforce
    Given I had a token and instance to connect with Salesforce
    And I have a campaign  with <name>
    When I send a request to create a new campaign
    Then A campaing has been created

    Examples:
      | name |
      | "Real Campaing forever" |
      | "Selenium Academy Campaing" |
      | "Silva Steve Campaing" |
