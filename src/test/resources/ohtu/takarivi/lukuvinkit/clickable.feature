Feature: As an user, I can click URLs and ISBNs

  Scenario: user can click URLs
    Given test user is logged in
    When viewing the link tip with title "test reading tip 3"
    Then URL goes to "https://example.com/"

  Scenario: user can click ISBNs
    Given test user is logged in
    When viewing the book tip with title "test reading tip 4"
    Then ISBN can be clicked
