Feature: When I try to add test data, there is no error

  Scenario: test data entry does not cause an error
    Given test data entry is enabled
    When adding test data
    Then test data is added successfully
