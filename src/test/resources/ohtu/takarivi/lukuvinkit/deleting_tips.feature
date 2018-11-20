Feature: After logging in, I can delete my own tips but not other peoples' tips

  Scenario: user can see and delete existent tip by that user
    Given test user is logged in
    When tip with title "test reading tip 1" is deleted
    Then tip with title "test reading tip 1" is no longer visible

  Scenario: user can see and delete existent tip
    Given test user is logged in
    When browsing tips
    Then tip with title "test reading tip 2" is not visible