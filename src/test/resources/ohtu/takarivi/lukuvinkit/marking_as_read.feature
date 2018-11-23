Feature: As an user, I can mark reading tips as having been read

  Scenario: user can mark a book tip as read
    Given test user is logged in and browsing book tips
    When book tip with title "test reading tip 4" is marked as read
    Then book tip with title "test reading tip 4" has been marked as read

  Scenario: user can mark a book tip as read without marking other tips as such
    Given test user is logged in and browsing book tips
    When book tip with title "test reading tip 4" is marked as read
    Then book tip with title "test reading tip 5" has not been marked as read
