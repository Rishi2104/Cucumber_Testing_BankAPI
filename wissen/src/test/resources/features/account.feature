Feature: Bank Account Management

  Scenario: Get list of accounts
    When I call GET /banking
    Then the response status should be 200
    And the response is not null
  
  Scenario: Get bank account by ID
    When I call GET /banking/1
    Then the response status should be 200
    And the response account should contain "ACC1001"

  Scenario: No account by ID
    When I call GET /banking/121
    Then the response status empty should be 404

