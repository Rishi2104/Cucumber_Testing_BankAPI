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

    Scenario: Add account
      Given I have the following bank details :
      | customerName | accountNumber | balance |
      | Alison dier | AC10121      | 10201021|
      | Alison Wire | AC10122      | 1101021|
      When I send this request to /insert with bank details
      Then the response status should be 201
   Scenario: Update account
     Given I have the following bank details :
   |customerName | accountNumber | balance |
   | Alison Hire | AC10121      | 10201021|
     When I call PUT /banking/6
     Then the response status should be 200
     When I call GET /banking/121
     Then the response status should be 404
