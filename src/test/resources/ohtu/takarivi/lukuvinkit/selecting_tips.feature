Feature: After logging in, I can select tips and view them

  Scenario: user can see selected tips
    Given test user is logged in and browsing book tips
    When selecting tip with title "test reading tip 4" and browsing is selected
    Then tip with title "test reading tip 4" is visible

  Scenario: user can see selected tips as text
    Given test user is logged in and browsing book tips
    When selecting tip with title "test reading tip 4" and exportText is selected
    Then tip with title "test reading tip 4" is visible