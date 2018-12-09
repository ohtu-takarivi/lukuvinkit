Feature: As an user, I can set the comment of a reading tip

  Scenario: user can add and edit the comment to a reading tip
    Given test user is logged in
    And viewing a book tip with the title "test reading tip 4"
    When the comment is set to "test comment"
    Then the comment of the reading tip is "test comment"

  Scenario: user can remove the comment to a reading tip
    Given test user is logged in
    And viewing a book tip with the title "test reading tip 4"
    When the comment is set to ""
    Then the comment of the reading tip is ""
