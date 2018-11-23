Feature: After logging in, I can browse tips

  Scenario: user can see book tips
    Given test user is logged in
    When browsing book tips
    Then tip with title "test reading tip 4" is visible

  Scenario: user can see link tips
    Given test user is logged in
    When browsing link tips
    Then tip with title "test reading tip 3" is visible
